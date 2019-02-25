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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author Carlos Romel Pereira da Silva, carlos.romel@gmail.com
 */
public class SignatureTest {

    private static final CrowdFunding REPORT = new PicPayCrowdFunding();
    private Ledger firstLedger;
    private Ledger secondLedger;
    private Signature firstSignature;
    private Signature secondSignature;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");

    @BeforeClass
    public static void startup() {
        REPORT.load(new File("src/test/resources/basicSample.csv"));
    }

    @Before
    public void before() {
        this.firstLedger = REPORT.getLedgers().get(0);
        this.firstSignature = this.firstLedger.getSignature();
        this.secondLedger = REPORT.getLedgers().get(1);
        this.secondSignature = this.secondLedger.getSignature();
    }

    @Test
    public void testCancellationDate() throws ParseException {

        assertEquals("A leitura da data do cancelamento da assinatura do assinante está incorreta.",
                     sdf.parse("04/08/2018 18:52"),
                     this.secondSignature.getCancelattionDate());
    }

    @Test
    public void testDueDate() throws ParseException {

        assertEquals("A leitura da data do cancelamento da assinatura do assinante está incorreta.",
                     sdf.parse("29/06/2018 15:44"),
                     this.secondSignature.getDueDate());
    }

    @Test
    public void testId() {

        assertEquals("A leitura do ID da assinatura do assinante está incorreta.",
                     (Long) 1024L, this.firstSignature.getId());
    }

    @Test
    public void testReasonOfCancellation() {

        assertEquals("A leitura da data do cancelamento da assinatura do assinante está incorreta.",
                     "Exilio politico",
                     this.secondSignature.getReasonOfCancellation());
    }

    @Test
    public void testSignatureDate() throws ParseException {

        assertEquals("A leitura do ID da assinatura do assinante está incorreta.",
                     sdf.parse("30/05/2018 15:40"),
                     this.firstSignature.getSignatureDate());
    }
}
