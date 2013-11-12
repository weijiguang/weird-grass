/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ili.base.entities.hr.attendance;

import java.sql.Date;
import net.ili.base.entities.IdEntity;

/**
 * 外出信息
 *
 * @author Weir
 */
public class Out extends IdEntity {

    private static final long serialVersionUID = 4217938414162669870L;
    private Date beginDate;
    private Date endDate;
    private String status;
    //批准
    private Boolean approved;
    //事由
    private String reason;
}
