/**
 * Copyright (C) 2019 Carlos Romel Pereira da Silva, carlos.romel@gmail.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package br.com.atlaspodcast.crowdfunding.util;

import br.com.atlaspodcast.crowdfunding.report.Individual;
import br.com.atlaspodcast.crowdfunding.report.Ledger;
import br.com.atlaspodcast.crowdfunding.report.Signature;
import br.com.atlaspodcast.crowdfunding.report.SignatureStatus;
import br.com.atlaspodcast.crowdfunding.report.picpay.CrowdFunding;
import br.com.atlaspodcast.crowdfunding.report.picpay.PicPayCrowdFunding;
import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map.Entry;

/**
 *
 * @author Carlos Romel Pereira da Silva, carlos.romel@gmail.com
 */
public class Main {

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private FundDetail fd = new FundDetail();
    private CrowdFunding cf = new PicPayCrowdFunding();

    public static void main(String[] args) {

        Main main = new Main();
        main.cf.load(new File("src/test/resources/relatorio-editado.csv"));
        main.showUsersToExclude(main.cf,
                                new GregorianCalendar(2018, 9, 1).getTime());

        if (args.length > 0) {

            switch (args[0]) {
                case "--signatures-by-status":
                    main.showSignaturesByStatus(main.cf);
                    break;
                case "--sum-active-signatures":
                    main.showSumActiveSignatures(main.cf);
                    break;
                case "--sum-cancelled-signatures":
                    main.showSumCancelledSignatures(main.cf);
                    break;
                case "--users-to-exclude":
//                    main.showUsersToExclude(main.cf);
                    main.showUsersToExclude(main.cf,
                                            new GregorianCalendar(2018, 0, 1).getTime());
                    break;
                default:
                    help();
            }
        } else {
            help();
        }
    }

    private static void help() {
        System.out.println("Sintaxe:");
        System.out.println("java -jar crowdfundingreportparser.jar [opcao]");
        System.out.println("--signatures-by-status");
        System.out.println("--sum-active-signatures");
        System.out.println("--sum-cancelled-signatures");
    }

    private void showSignaturesByStatus(CrowdFunding cf) {
        Integer total = 0;

        System.out.println("Estado das assinaturas");

        for (Entry<SignatureStatus, Integer> signatureStatus : fd.getSignaturesByStatus(cf).entrySet()) {
            SignatureStatus status = signatureStatus.getKey();
            Integer statusCount = signatureStatus.getValue();
            total += statusCount;

            System.out.printf("%d\t%s%n", statusCount, status);
        }

        System.out.printf("%d\tTotal", total);
    }

    private void showSumActiveSignatures(CrowdFunding cf) {
        BigDecimal total = new BigDecimal(0);
        Integer count = 0;

        for (Entry<String, BigDecimal> activeSignature : fd.getActiveSignatures(cf).entrySet()) {
            String title = activeSignature.getKey();
            BigDecimal sum = activeSignature.getValue();

            ++count;
            total = total.add(sum);

            System.out.printf("%s\t%s%n", sum, title);
        }

        System.out.printf("%d\t%s\tQuantidade/valor de assinaturas ativas.",
                          count, total);
    }

    private void showSumCancelledSignatures(CrowdFunding cf) {
        BigDecimal total = new BigDecimal(0);
        Integer count = 0;

        for (Entry<String, BigDecimal> activeSignature : fd.getCancelledSignatures(cf).entrySet()) {
            String title = activeSignature.getKey();
            BigDecimal sum = activeSignature.getValue();

            ++count;
            total = total.add(sum);

            System.out.printf("%s\t%s%n", sum, title);
        }

        System.out.printf("%d\t%s\tQuantidade/valor de assinaturas canceladas.",
                          count, total);
    }

    private void showUsersToExclude(CrowdFunding cf) {
        showUsersToExclude(cf, new GregorianCalendar(2018, 0, 1).getTime());
    }

    private void showUsersToExclude(CrowdFunding cf, Date startDate) {
        List<Individual> individuals = new ArrayList<>();
        boolean showHeader = false;

        for (Ledger ledger : cf.getLedgers()) {
            Signature signature = ledger.getSignature();
            Individual individual = signature.getIndividual();

            if (ledger.getSignatureStatus() == SignatureStatus.CANCELLED
                && signature.getCancelattionDate().after(startDate)) {
                if (!showHeader) {
                    System.out.printf("%s\t%s\t%s\t\t\t%s%n",
                                      "Último pagamento",
                                      "Data do cancelamento",
                                      "E-mail",
                                      "Usuário",
                                      "Nome completo");
                    System.out.println("----------------------------------------------------------------------------------");
                    showHeader = true;
                }
                System.out.printf("%s\t%s\t%s\t%s%n",
                                  sdf.format(signature.getDueDate()),
                                  sdf.format(signature.getCancelattionDate()),
                                  individual.getEmail(),
                                  individual.getUser(),
                                  individual.getName());
                individuals.add(individual);
            }
        }

        if (individuals.size() > 0) {
            System.out.printf("%d\tAssinaturas canceladas.%n", individuals.size());
        } else {
            System.out.println("Nenhuma assinatura cancelada!");
        }
    }

    private void showUsersToInclude(CrowdFunding cf, Date startDate) {
        List<Individual> individuals = new ArrayList<>();
        boolean showHeader = false;

        for (Ledger ledger : cf.getLedgers()) {
            Signature signature = ledger.getSignature();
            Individual individual = signature.getIndividual();

            if (ledger.getSignatureStatus() == SignatureStatus.ACTIVE
                && signature.getCancelattionDate().after(startDate)) {
                if (!showHeader) {
                    System.out.printf("%s\t%s\t%s\t\t\t%s%n",
                                      "Data da assinatura",
                                      "Último pagamento",
                                      "E-mail",
                                      "Usuário",
                                      "Nome completo");
                    System.out.println("----------------------------------------------------------------------------------");
                    showHeader = true;
                }
                System.out.printf("%s\t%s\t%s\t%s%n",
                                  sdf.format(signature.getSignatureDate()),
                                  sdf.format(signature.getDueDate()),
                                  individual.getEmail(),
                                  individual.getUser(),
                                  individual.getName());
                individuals.add(individual);
            }
        }

        if (individuals.size() > 0) {
            System.out.printf("%d\tAssinaturas canceladas.%n", individuals.size());
        } else {
            System.out.println("Nenhuma assinatura cancelada!");
        }
    }
}
