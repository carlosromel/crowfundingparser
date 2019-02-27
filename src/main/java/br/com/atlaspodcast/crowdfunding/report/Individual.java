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

    /**
     * Construtor padrão.
     *
     * @param id         Identificador do apoiador.
     * @param name       Nome do apoiador.
     * @param user       Usuário do apoiador
     * @param email      E-mail do apoiador.
     * @param phone      Telefone do apoiador.
     * @param personalId Identificacor pessoal do apoiador (CPF).
     */
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

    /**
     * Identificador do apoiador.
     *
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     * Nome do apoiador.
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Usuário do apoiador.
     *
     * @return
     */
    public String getUser() {
        return user;
    }

    /**
     * E-mail do apoiador.
     *
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * Telefone do apoiador.
     *
     * @return
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Identificacor pessoal do apoiador (CPF).
     *
     * @return
     */
    public String getPersonalId() {
        return personalId;
    }
}
