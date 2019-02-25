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
package br.com.atlaspodcast.crowdfunding.report.picpay;

import java.io.File;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 *
 * @author Carlos Romel Pereira da Silva, carlos.romel@gmail.com
 */
public class ReportParserTest {

    private static final CrowdFunding REPORT = new PicPayCrowdFunding();

    @BeforeClass
    public static void before() {
        REPORT.load(new File("src/test/resources/relatorio-editado.csv"));
    }

    @Test
    public void fileLoadTest() {
        CrowdFunding report = new PicPayCrowdFunding();

        assertTrue("O relatório informado não existe.",
                   report.load(new File("src/test/resources/relatorio-editado.csv")));
    }
}
