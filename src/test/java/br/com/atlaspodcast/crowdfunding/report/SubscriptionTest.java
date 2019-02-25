/*
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

import br.com.atlaspodcast.crowdfunding.report.picpay.CrowdFunding;
import br.com.atlaspodcast.crowdfunding.report.picpay.PicPayCrowdFunding;
import java.io.File;
import java.math.BigDecimal;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author Carlos Romel Pereira da Silva, carlos.romel@gmail.com
 */
public class SubscriptionTest {

    private static final CrowdFunding REPORT = new PicPayCrowdFunding();
    private Ledger firstLedger;
    private Ledger secondLedger;
    private Signature firstSignature;
    private Signature secondSignature;
    private Subscription firstSubscription;
    private Subscription secondSubscription;

    @BeforeClass
    public static void startup() {
        REPORT.load(new File("src/test/resources/basicSample.csv"));
    }

    @Before
    public void before() {
        this.firstLedger = REPORT.getLedgers().get(0);
        this.firstSignature = this.firstLedger.getSignature();
        this.firstSubscription = this.firstSignature.getSubscription();

        this.secondLedger = REPORT.getLedgers().get(1);
        this.secondSignature = this.secondLedger.getSignature();
        this.secondSubscription = this.secondSignature.getSubscription();
    }

    @Test
    public void testFirstTitle() {

        assertEquals("A leitura do título da primeira assinatura está incoreta.",
                     "Plano de R$ 15,00",
                     this.firstSubscription.getTitle());
    }

    @Test
    public void testFirstValue() {

        assertEquals("A leitura do valor da primeira assinatura está incoreta.",
                     new BigDecimal(15),
                     this.firstSubscription.getValue());
    }

    @Test
    public void testSecondTitle() {

        assertEquals("A leitura do título da segunda assinatura está incoreta.",
                     "Plano de R$ 55,00",
                     this.secondSubscription.getTitle());
    }

    @Test
    public void testSecondValue() {

        assertEquals("A leitura do valor da segunda assinatura está incoreta.",
                     new BigDecimal(55),
                     this.secondSubscription.getValue());
    }
}
