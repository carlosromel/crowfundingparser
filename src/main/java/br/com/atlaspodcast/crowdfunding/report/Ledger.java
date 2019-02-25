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

import java.math.BigDecimal;
import java.util.Date;

/**
 * Carteira de assinantes.
 *
 * @author Carlos Romel Pereira da Silva, carlos.romel@gmail.com
 */
public class Ledger {

    /**
     * Data do último pagamento.
     */
    private final Date lastPayment;
    /**
     * Status da assinatura.
     */
    private final SignatureStatus signatureStatus;
    private final Signature signature;
    /**
     * Valor estornado.
     */
    private final BigDecimal reversalValue;

    public Ledger(Date lastPayment,
                  SignatureStatus status,
                  Signature signature,
                  BigDecimal reversalValue) {
        this.lastPayment = lastPayment;
        this.signatureStatus = status;
        this.signature = signature;
        this.reversalValue = reversalValue;
    }

    public Date getLastPayment() {
        return lastPayment;
    }

    public SignatureStatus getSignatureStatus() {
        return signatureStatus;
    }

    public Signature getSignature() {
        return signature;
    }

    public BigDecimal getReversalValue() {
        return reversalValue;
    }
}
