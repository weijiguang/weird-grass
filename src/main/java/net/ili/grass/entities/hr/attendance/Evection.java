/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ili.grass.entities.hr.attendance;

import java.sql.Date;
import net.ili.grass.entities.IdEntity;

/**
 * 出差信息
 *
 * @author Weir
 */
public class Evection extends IdEntity {

    private static final long serialVersionUID = -97719854383109448L;
    private Date beginDate;
    private Date endDate;
    
}
