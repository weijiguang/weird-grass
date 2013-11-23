/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ili.grass.entities.hr.attendance;

import java.sql.Date;
import net.ili.grass.entities.IdEntity;

/**
 * 节假日信息
 *
 * @author Weir
 */
public class Holiday extends IdEntity {

    private static final long serialVersionUID = -2264813081248149306L;
    private Date beginDate;
    private Date endDate;
}
