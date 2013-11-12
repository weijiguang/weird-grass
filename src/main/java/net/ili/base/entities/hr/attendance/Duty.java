/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ili.base.entities.hr.attendance;

import net.ili.base.entities.IdEntity;

/**
 * 职务信息
 *
 * @author Weir
 */
public class Duty extends IdEntity {

    private static final long serialVersionUID = -3610483336314037168L;

}

//
//CREATE TABLE `attend_duty` (
//  `USER_ID` varchar(20) NOT NULL default '',
//  `REGISTER_TYPE` char(1) NOT NULL default '',
//  `REGISTER_TIME` datetime NOT NULL default '0000-00-00 00:00:00',
//  `REGISTER_IP` varchar(20) NOT NULL default '',
//  `REMARK` text,
//  KEY `USER_ID` (`USER_ID`)
//) ENGINE=MyISAM DEFAULT CHARSET=utf8;
