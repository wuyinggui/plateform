package com.ucar.model.service.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import net.sf.json.JSONObject;

import com.ucar.model.bean.LBSUser;
import com.ucar.model.service.LBSUserService;
@Service("lBSUserService")
@Scope("prototype")
public class LBSUserServiceImpl implements LBSUserService{

	@Override
	public JSONObject LBSLoginValidate(LBSUser entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
