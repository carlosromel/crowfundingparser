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

import br.com.atlaspodcast.crowdfunding.report.picpay.CrowdFunding;
import br.com.atlaspodcast.crowdfunding.report.picpay.PicPayCrowdFunding;
import java.io.File;
import java.math.BigDecimal;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author Carlos Romel Pereira da Silva, carlos.romel@gmail.com
 */
public class FundDetailTest {

    private static FundDetail FD;
    private static CrowdFunding CF;

    @BeforeClass
    public static void beforeClass() {
        FD = new FundDetail();
        CF = new PicPayCrowdFunding();
        CF.load(new File("src/test/resources/relatorio-editado.csv"));
    }

    @Test
    public void testActiveSignatures() {
        assertEquals("A quantidade de assinaturas ativas está incorreta.",
                     (Integer) 47, FD.getCountActiveSignatures(CF));
    }

    @Test
    public void testCancelledSignatures() {
        assertEquals("A quantidade de assinaturas ativas está incorreta.",
                     (Integer) 6, FD.getCountCancelledSignatures(CF));
    }

    @Test
    public void testTotalSignatures() {
        assertEquals("A quantidade de assinaturas ativas está incorreta.",
                     53, CF.getLedgers().size());
    }

    @Test
    public void testSignaturesByExistingSignature() {
        String title = "Plano de R$ 15,00";

        assertFalse("O plano informado não foi encontrado.",
                    FD.getSignaturesBySignature(CF, title).isEmpty());
    }

    @Test
    public void testSignaturesByInexistingSignature() {
        String title = "Plano de R$ 1500,00";

        assertTrue("O plano informado foi encontrado mesmo não existindo.",
                   FD.getSignaturesBySignature(CF, title).isEmpty());
    }

    @Test
    public void testSignatureByStatus() {
        assertFalse("A relação das assinaturas por estado está incorreta.",
                    FD.getSignaturesByStatus(CF).isEmpty());
    }

    @Test
    public void testSumActiveSignaturesNotEmpty() {
        assertFalse("A soma das assinaturas está incorreta.",
                    FD.getSumActiveSignatures(CF).equals(BigDecimal.ZERO));
    }

    @Test
    public void testSumActiveSignatures() {
        assertEquals("A soma das assinaturas ativas está incorreta.",
                     new BigDecimal(404), FD.getSumActiveSignatures(CF));
    }

    @Test
    public void testSumCancelledSignatures() {
        assertEquals("A soma das assinaturas canceladas está incorreta.",
                     new BigDecimal(55), FD.getSumCancelledSignatures(CF));
    }

    @Test
    public void testSumAllSignatures() {
        assertEquals("A soma de todas as assinaturas registradas está incorreta.",
                     new BigDecimal(459), FD.getSumAllSignatures(CF));
    }
}
