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
package br.com.atlaspodcast.crowdfunding.report.picpay;

import br.com.atlaspodcast.crowdfunding.report.Individual;
import br.com.atlaspodcast.crowdfunding.report.Ledger;
import br.com.atlaspodcast.crowdfunding.report.Signature;
import br.com.atlaspodcast.crowdfunding.report.SignatureStatus;
import br.com.atlaspodcast.crowdfunding.report.Subscription;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos Romel Pereira da Silva, carlos.romel@gmail.com
 */
public class PicPayCrowdFunding implements CrowdFunding {

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private final List<Ledger> ledgers;

    /*
     * private Individual individual; private Subscription subscription;
     * this.individual = new Individual(); this.subscription = new
     * Subscription();
     *
     */
    public PicPayCrowdFunding() {
        this.ledgers = new ArrayList<>();
    }

    @Override
    public boolean load(File reportFile) {
        boolean success = reportFile.exists();
        Path path = reportFile.toPath();

        if (success) {
            try {
                Charset cs = Charset.forName("ISO-8859-1");
                List<String> lines = Files.readAllLines(path, cs);

                for (int l = 1; l < lines.size(); ++l) {
                    importRecord(lines.get(l));
                }
            } catch (IOException ex) {
                Logger.getAnonymousLogger().log(Level.SEVERE, "O arquivo não pode ser lido.", ex);
            }
        }

        return success;
    }

    @Override
    public List<Ledger> getLedgers() {
        return this.ledgers;
    }

    private void importRecord(String record) {
        String[] parts = record.split(";");

        for (int p = 0; p < parts.length; ++p) {
            parts[p] = parts[p].trim();
        }

        this.ledgers.add(getLedger(parts));
    }

    private Individual getIndividual(String[] parts) {
        String individualName = parts[0];
        String individualUser = parts[1];
        String individualEmail = parts[2];
        String individualPhone = parts[3];
        String recordIndividualId = parts[13];
        String individualPersonalId = parts[15];

        Long individualId = null;

        try {
            individualId = Long.parseLong(recordIndividualId);
        } catch (NumberFormatException ex) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "O ID do assinante não pode ser lida.", ex);
        }

        return new Individual(
                individualId,
                individualName,
                individualUser,
                individualEmail,
                individualPhone,
                individualPersonalId);
    }

    private Subscription getSubscription(String[] parts) {
        String subscriptionTitle = parts[4];
        String recordSubscriptionValue = parts[5];

        BigDecimal subscriptionValue = new BigDecimal(recordSubscriptionValue);

        return new Subscription(
                subscriptionTitle,
                subscriptionValue);
    }

    /*
     * String recordSignatureValue = parts[7]; BigDecimal signatureValue = new
     * BigDecimal(recordSignatureValue);
     */
    private Signature getSignature(String[] parts) {
        String recordSignatureDate = parts[6];
        String recordDueDate = parts[8];
        String reasonOfCancellation = parts[10];
        String recordCancellationDate = parts[11];
        String recordSignatureId = parts[14];

        Date signatureDate = null;
        Long signatureId = null;
        Date cancellationDate = null;
        Date dueDate = null;

        try {
            signatureDate = sdf.parse(recordSignatureDate);
        } catch (ParseException ex) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "A data da assinatura não pode ser lida.", ex);
        }

        try {
            signatureId = Long.parseLong(recordSignatureId);
        } catch (NumberFormatException ex) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "A data do próximo pagamento não pode ser lida.", ex);
        }

        if (!recordCancellationDate.isEmpty()) {
            try {
                cancellationDate = sdf.parse(recordCancellationDate);
            } catch (ParseException ex) {
                Logger.getAnonymousLogger().log(Level.SEVERE, "A data do cancelamento não pode ser lida.", ex);
            }
        }

        try {
            dueDate = sdf.parse(recordDueDate);
        } catch (ParseException ex) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "A data do próximo pagamento não pode ser lida.", ex);
        }

        return new Signature(
                signatureId,
                getIndividual(parts),
                getSubscription(parts),
                reasonOfCancellation,
                cancellationDate,
                dueDate,
                signatureDate);
    }

    private Ledger getLedger(String[] parts) {

        String recordStatus = parts[9];
        String recordReversalValue = parts[12];
        String recordSignatureLastPayment = parts[16];

        if (recordReversalValue.isEmpty()) {
            recordReversalValue = "0";
        }

        Date signatureLastPayment = null;
        SignatureStatus status = null;
        BigDecimal reversalValue = new BigDecimal(recordReversalValue);

        try {
            signatureLastPayment = sdf.parse(recordSignatureLastPayment);
        } catch (ParseException ex) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "A data da assinatura não pode ser lida.", ex);
        }

        if ("ativa".equals(recordStatus)) {
            status = SignatureStatus.ACTIVE;
        } else {
            status = SignatureStatus.CANCELLED;
        }

        return new Ledger(
                signatureLastPayment,
                status,
                getSignature(parts),
                reversalValue);
    }
}
