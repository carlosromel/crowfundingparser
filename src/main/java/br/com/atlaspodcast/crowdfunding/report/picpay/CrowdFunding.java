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

import br.com.atlaspodcast.crowdfunding.report.Ledger;
import java.io.File;
import java.util.List;

/**
 *
 * @author Carlos Romel Pereira da Silva, carlos.romel@gmail.com
 */
public interface CrowdFunding {

    /**
     * Carrega os dados para análise.
     *
     * @param reportFile Caminho do relatório.
     *
     * @return Verdadeiro quando o arquivo existe e pode ser lido.
     */
    boolean load(File reportFile);

    /**
     * Retorna a relação dos registros.
     *
     * @return Relação dos registros.
     */
    List<Ledger> getLedgers();
}
