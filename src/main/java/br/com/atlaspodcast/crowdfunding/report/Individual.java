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

/**
 * Representa um assinante.
 *
 * @author Carlos Romel Pereira da Silva, carlos.romel@gmail.com
 */
public class Individual {

    /**
     * Id do assinante.
     */
    private final Long id;
    /**
     * Nome do assinante.
     */
    private final String name;
    /**
     * Nome de usuário do assinante.
     */
    private final String user;
    /**
     * Email do assinante.
     */
    private final String email;
    /**
     * Telefone do assinante.
     */
    private final String phone;
    /**
     * CPF do assinante.
     */
    private final String personalId;

    public Individual(Long id,
                      String name,
                      String user,
                      String email,
                      String phone,
                      String personalId) {
        this.id = id;
        this.name = name;
        this.user = user;
        this.email = email;
        this.phone = phone;
        this.personalId = personalId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUser() {
        return user;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPersonalId() {
        return personalId;
    }
}
