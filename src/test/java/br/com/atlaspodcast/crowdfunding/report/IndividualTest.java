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

import br.com.atlaspodcast.crowdfunding.report.Individual;
import br.com.atlaspodcast.crowdfunding.report.Ledger;
import br.com.atlaspodcast.crowdfunding.report.Signature;
import br.com.atlaspodcast.crowdfunding.report.picpay.CrowdFunding;
import br.com.atlaspodcast.crowdfunding.report.picpay.PicPayCrowdFunding;
import java.io.File;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Carlos Romel Pereira da Silva, carlos.romel@gmail.com
 */
public class IndividualTest {

    private static final CrowdFunding REPORT = new PicPayCrowdFunding();
    private Ledger ledger;
    private Signature signature;
    private Individual individual;

    @BeforeClass
    public static void startup() {
        REPORT.load(new File("src/test/resources/basicSample.csv"));
    }

    @Before
    public void before() {
        this.ledger = REPORT.getLedgers().get(0);
        this.signature = this.ledger.getSignature();
        this.individual = this.signature.getIndividual();
    }

    @Test
    public void testName() {

        Assert.assertEquals("A leitura do nome do assinante está incorreta.",
                            "Crash Test Dummy", this.individual.getName());
    }

    @Test
    public void testUser() {

        Assert.assertEquals("A leitura do usuário do assinante está incorreta.",
                            "crash.test.dummy", this.individual.getUser());
    }

    @Test
    public void testPhone() {

        Assert.assertEquals("A leitura do telefone do assinante está incorreta.",
                            "Crash Test Dummy", this.individual.getName());
    }

    @Test
    public void testPersonalId() {

        Assert.assertEquals("A leitura do CPF do assinante está incorreta.",
                            "123.456.789-01", this.individual.getPersonalId());
    }
}
