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
package br.com.atlaspodcast.crowdfunding.report;

import java.util.Date;

/**
 * Assinatura de um plano.
 *
 * @author Carlos Romel Pereira da Silva, carlos.romel@gmail.com
 */
public class Signature {

    /**
     * Id da assinatura.
     */
    private final Long id;
    private final Individual individual;
    private final Subscription subscription;
    /**
     * Motivo do cancelamento.
     */
    private final String reasonOfCancellation;
    /**
     * Data do cancelamento.
     */
    private final Date cancelattionDate;
    /**
     * Data do próximo pagamento.
     */
    private final Date dueDate;
    /**
     * Data da assinatura.
     */
    private final Date signatureDate;

    public Signature(Long id,
                     Individual individual,
                     Subscription subscription,
                     String reasonOfCancellation,
                     Date cancelattionDate,
                     Date dueDate,
                     Date signatureDate) {
        this.id = id;
        this.individual = individual;
        this.subscription = subscription;
        this.reasonOfCancellation = reasonOfCancellation;
        this.cancelattionDate = cancelattionDate;
        this.dueDate = dueDate;
        this.signatureDate = signatureDate;
    }

    public Long getId() {
        return id;
    }

    public Individual getIndividual() {
        return individual;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public String getReasonOfCancellation() {
        return reasonOfCancellation;
    }

    public Date getCancelattionDate() {
        return cancelattionDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public Date getSignatureDate() {
        return signatureDate;
    }
}
