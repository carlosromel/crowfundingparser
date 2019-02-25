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

import br.com.atlaspodcast.crowdfunding.report.SignatureStatus;
import br.com.atlaspodcast.crowdfunding.report.picpay.CrowdFunding;
import br.com.atlaspodcast.crowdfunding.report.picpay.PicPayCrowdFunding;
import java.io.File;
import java.math.BigDecimal;
import java.util.Map.Entry;

/**
 *
 * @author Carlos Romel Pereira da Silva, carlos.romel@gmail.com
 */
public class Main {

    private FundDetail fd = new FundDetail();
    private CrowdFunding cf = new PicPayCrowdFunding();

    public static void main(String[] args) {

        Main main = new Main();
        main.cf.load(new File("src/test/resources/relatorio-editado.csv"));

        if (args.length > 0) {

            switch (args[0]) {
                case "--signatures-by-status":
                    main.printSignaturesByStatus(main.cf);
                    break;
                case "--sum-active-signatures":
                    main.printSumActiveSignatures(main.cf);
                    break;
                case "--sum-cancelled-signatures":
                    main.printSumCancelledSignatures(main.cf);
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

    private void printSignaturesByStatus(CrowdFunding cf) {
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

    private void printSumActiveSignatures(CrowdFunding cf) {
        BigDecimal total = new BigDecimal(0);
        Integer count = 0;

        for (Entry<String, BigDecimal> activeSignatures : fd.getActiveSignatures(cf).entrySet()) {
            String title = activeSignatures.getKey();
            BigDecimal sum = activeSignatures.getValue();

            ++count;
            total = total.add(sum);

            System.out.printf("%s\t%s%n", sum, title);
        }

        System.out.printf("%d\t%s\tQuantidade/valor de assinaturas ativas.",
                          count, total);
    }

    private void printSumCancelledSignatures(CrowdFunding cf) {
        BigDecimal total = new BigDecimal(0);
        Integer count = 0;

        for (Entry<String, BigDecimal> activeSignatures : fd.getCancelledSignatures(cf).entrySet()) {
            String title = activeSignatures.getKey();
            BigDecimal sum = activeSignatures.getValue();

            ++count;
            total = total.add(sum);

            System.out.printf("%s\t%s%n", sum, title);
        }

        System.out.printf("%d\t%s\tQuantidade/valor de assinaturas canceladas.",
                          count, total);
    }
}
