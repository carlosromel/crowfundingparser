/**
 * Copyright (C) 2018 Carlos Romel Pereira da Silva, carlos.romel@gmail.com
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

import br.com.atlaspodcast.crowdfunding.report.Ledger;
import br.com.atlaspodcast.crowdfunding.report.Signature;
import br.com.atlaspodcast.crowdfunding.report.SignatureStatus;
import br.com.atlaspodcast.crowdfunding.report.Subscription;
import br.com.atlaspodcast.crowdfunding.report.picpay.CrowdFunding;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author Carlos Romel Pereira da Silva, carlos.romel@gmail.com
 */
public class FundDetail {

    /**
     * Retorna o mapa das assinaturas por estado.
     *
     * @param cf Mecanismo de financiamento.
     *
     * @return Quantidade de assinaturas canceladas.
     */
    public Map<SignatureStatus, Integer> getSignaturesByStatus(CrowdFunding cf) {
        Map<SignatureStatus, Integer> total = new HashMap<>();

        Integer active = 0;
        Integer cancelled = 0;
        Integer inactive = 0;
        Integer suspended = 0;

        for (Ledger ledger : cf.getLedgers()) {
            switch (ledger.getSignatureStatus()) {
                case ACTIVE:
                    ++active;
                    break;
                case CANCELLED:
                    ++cancelled;
                    break;
                case INACTIVE:
                    ++inactive;
                    break;
                case SUSPENDED:
                    ++suspended;
                    break;
            }
        }

        total.put(SignatureStatus.ACTIVE, active);
        total.put(SignatureStatus.CANCELLED, cancelled);
        total.put(SignatureStatus.INACTIVE, inactive);
        total.put(SignatureStatus.SUSPENDED, suspended);

        return total;
    }

    /**
     * Retorna o total de assinaturas canceladas.
     *
     * @param cf Mecanismo de financiamento.
     *
     * @return Quantidade de assinaturas ativas.
     */
    public Integer getCountActiveSignatures(CrowdFunding cf) {
        Integer total = 0;

        for (Ledger ledger : cf.getLedgers()) {
            if (ledger.getSignatureStatus() == SignatureStatus.ACTIVE) {
                ++total;
            }
        }

        return total;
    }

    /**
     * Retorna o total de assinaturas canceladas.
     *
     * @param cf Mecanismo de financiamento.
     *
     * @return Quantidade de assinaturas canceladas.
     */
    public Integer getCountCancelledSignatures(CrowdFunding cf) {
        Integer total = 0;

        for (Ledger ledger : cf.getLedgers()) {
            if (ledger.getSignatureStatus() == SignatureStatus.CANCELLED) {
                ++total;
            }
        }

        return total;
    }

    /**
     * Retorna o mapa das assinaturas, agrupadas por plano.
     *
     * @param cf Mecanismo de financiamento.
     *
     * @return Mapa das assinaturas.
     */
    public Map<String, BigDecimal> getSignatures(CrowdFunding cf) {
        Map<String, BigDecimal> signatures = new HashMap<>();
        for (Ledger ledger : cf.getLedgers()) {
            Signature signature = ledger.getSignature();
            Subscription subscription = signature.getSubscription();
            String title = subscription.getTitle();

            if (signatures.containsKey(title)) {
                signatures.put(title, signatures.get(title).add(subscription.getValue()));
            } else {
                signatures.put(title, subscription.getValue());
            }
        }

        return signatures;
    }

    /**
     * Retorna o mapa das assinaturas ativas.
     *
     * @param cf Mecanismo de financiamento.
     *
     * @return Mapa das assinaturas ativas.
     */
    public Map<String, BigDecimal> getActiveSignatures(CrowdFunding cf) {

        return getSignatures(cf, SignatureStatus.ACTIVE);
    }

    /**
     * Retorna o mapa das assinaturas canceladas.
     *
     * @param cf Mecanismo de financiamento.
     *
     * @return Mapa das assinaturas canceladas.
     */
    public Map<String, BigDecimal> getCancelledSignatures(CrowdFunding cf) {

        return getSignatures(cf, SignatureStatus.CANCELLED);
    }

    /**
     * Retorna o mapa de todas as assinaturas.
     *
     * @param cf Mecanismo de financiamento.
     *
     * @return Mapa das assinaturas registradas.
     */
    public Map<String, BigDecimal> getAllSignatures(CrowdFunding cf) {
        Map<String, BigDecimal> activeSignatures = new HashMap<>();

        for (Ledger ledger : cf.getLedgers()) {
            Signature signature = ledger.getSignature();
            Subscription subscription = signature.getSubscription();
            String title = subscription.getTitle();

            if (activeSignatures.containsKey(title)) {
                activeSignatures.put(title, activeSignatures.get(title).add(subscription.getValue()));
            } else {
                activeSignatures.put(title, subscription.getValue());
            }
        }

        return activeSignatures;
    }

    /**
     * Retorna o mapa das assinaturas, agrupada por plano de assinatura.
     *
     * @param cf Mecanismo de financiamento.
     * @param ss Estado da assinatura (ativa, cancelada, inativa, suspensa).
     *
     * @return Soma das assinaturas canceladas.
     */
    public Map<String, BigDecimal> getSignatures(CrowdFunding cf, SignatureStatus ss) {
        Map<String, BigDecimal> activeSignatures = new HashMap<>();

        for (Ledger ledger : cf.getLedgers()) {
            Signature signature = ledger.getSignature();
            Subscription subscription = signature.getSubscription();
            String title = subscription.getTitle();

            if (ledger.getSignatureStatus() == ss) {
                if (activeSignatures.containsKey(title)) {
                    activeSignatures.put(title, activeSignatures.get(title).add(subscription.getValue()));
                } else {
                    activeSignatures.put(title, subscription.getValue());
                }
            }
        }

        return activeSignatures;
    }

    /**
     * Retorna a soma das assinaturas ativas.
     *
     * @param cf Mecanismo de financiamento.
     *
     * @return Soma das assinaturas ativas.
     */
    public BigDecimal getSumActiveSignatures(CrowdFunding cf) {
        BigDecimal total = new BigDecimal(0);

        for (Entry<String, BigDecimal> sig : getActiveSignatures(cf).entrySet()) {
            BigDecimal sum = sig.getValue();

            total = total.add(sum);
        }

        return total;
    }

    /**
     * Retorna a soma das assinaturas canceladas.
     *
     * @param cf Mecanismo de financiamento.
     *
     * @return Soma das assinaturas canceladas.
     */
    public BigDecimal getSumCancelledSignatures(CrowdFunding cf) {
        BigDecimal total = new BigDecimal(0);

        for (Entry<String, BigDecimal> sig : getCancelledSignatures(cf).entrySet()) {
            BigDecimal sum = sig.getValue();

            total = total.add(sum);
        }

        return total;
    }

    /**
     * Retorna a soma de todas as assinaturas registradas.
     *
     * @param cf Mecanismo de financiamento.
     *
     * @return Soma das assinaturas registradas.
     */
    public BigDecimal getSumAllSignatures(CrowdFunding cf) {
        BigDecimal total = new BigDecimal(0);

        for (Entry<String, BigDecimal> sig : getAllSignatures(cf).entrySet()) {
            BigDecimal sum = sig.getValue();

            total = total.add(sum);
        }

        return total;
    }

    /**
     * Retorna o mapa de todas as assinaturas.
     *
     * @param cf            Mecanismo de financiamento.
     * @param signatureNeed Assinatura específcia a ser pesquisada.
     *
     * @return Mapa da assinatura encontrada.
     */
    public Map<String, BigDecimal> getSignaturesBySignature(CrowdFunding cf, String signatureNeed) {
        Map<String, BigDecimal> signatures = new HashMap<>();
        for (Ledger ledger : cf.getLedgers()) {
            Signature signature = ledger.getSignature();
            Subscription subscription = signature.getSubscription();
            String title = subscription.getTitle();

            if (title.equals(signatureNeed)) {
                if (signatures.containsKey(title)) {
                    signatures.put(title, signatures.get(title).add(subscription.getValue()));
                } else {
                    signatures.put(title, subscription.getValue());
                }
            }
        }

        return signatures;
    }
}
