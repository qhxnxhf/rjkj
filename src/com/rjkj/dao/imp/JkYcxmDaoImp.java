package com.rjkj.dao.imp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.rjkj.dao.JkTjUserDao;
import com.rjkj.dao.JkYcxmDao;
import com.rjkj.entities.JkTjxm;
import com.rjkj.entities.JkYcxm;
import com.rjkj.model.TjUser;
import com.rjkj.model.Tjxx;
import com.rjkj.util.web.Page;

@Transactional
@Component("JkYcxmDao")
public class JkYcxmDaoImp extends GenericDaoImpl<JkYcxm, Long> implements JkYcxmDao{

	@Override
	public List<JkYcxm> findAll(String query, Page page) {
		int ss=(page.getPageNo()-1)*page.getPageSize();
		int ee=page.getPageSize();
		//String hql=" WHERE o.sort.id = "+parentId+" "+query;
		int tt=this.getTotalCount(JkYcxm.class,query);
		page.setTotalRows(tt);
		return this.findAll(JkYcxm.class, query, ss, ee);
	}

	@Override
	public JkYcxm findMaxId() {
		String hql="select a from JkYcxm a where a.id IN(select max(b.id) from JkYcxm b)";
		List<JkYcxm> maxs=this.findAll(hql);
		if(maxs!=null&&maxs.size()>0){
			return maxs.get(0);
		}
		return null;
	}

	@Override
	public void saveXxcs(Tjxx tjx, List<JkTjxm> cstj,final HashMap<String, TjUser> userHash) {
		String name=tjx.getTjx().trim().toLowerCase();
		//String jg=tjx.getTjjg().trim().toLowerCase();
		
		String name2;
		//String jg2;
		//JkYcxm ycxm;
		for(JkTjxm tjxm:cstj){
			name2=tjxm.getName().trim().toLowerCase();
			if(name.equals(name2)){
				
				//对tjx 进行语义转换
				double yy=yyzh(tjx,tjxm);
				
				//分条件进行比较
				double sxl=0.0;
				if(tjxm.getSxValueL()!=null)
					sxl=tjxm.getSxValueL();
				
				double sxh=0.0;
				switch(tjxm.getType1Value()){
					case ">":{
						if(yy>=sxl){
							saveXm(tjx,tjxm,yy,userHash);
						}
						break;
					}
					case "<":{
						if(yy>0&&yy<=sxl){
							saveXm(tjx,tjxm,yy,userHash);
						}
						break;
					}
					case "=":{
						if(yy==sxl){
							saveXm(tjx,tjxm,yy,userHash);
						}
						break;
					}
					case "!":{
						if(yy!=sxl){
							saveXm(tjx,tjxm,yy,userHash);
						}
						break;
					}
					case "-":{
						if(tjxm.getSxValueH()!=null)
							sxh=tjxm.getSxValueH();
						if(sxl<=yy&&yy<=sxh){
							saveXm(tjx,tjxm,yy,userHash);
						}
						break;
					}
				
				}
				
			}
		}
		
	}
	
	
	
	public void saveXm(Tjxx tjx,JkTjxm tjxm,double jg,final HashMap<String, TjUser> userHash){
		JkYcxm ycxm=new JkYcxm();
		ycxm.setCardId(tjx.getCardId());
		ycxm.setDeptId(tjx.getDeptId());
		
		ycxm.setSex(tjx.getSex());
		ycxm.setTjxmMc(tjx.getTjx());
		ycxm.setTjDate(tjx.getTjDate());
		ycxm.setTjpc(tjx.getTjpc());
		ycxm.setTjType(tjx.getTjType());
		ycxm.setTjxm(tjxm);
		ycxm.setTjValue(jg);
		ycxm.setTjxxId(tjx.getId());
		
		int ages=this.getAges(tjx);
		ycxm.setTjAges(ages);
		
		TjUser user=userHash.get(tjx.getCardId());
		if(user!=null){
			if(user.getId()!=null)
			ycxm.setTjrId(user.getId());;
			if(user.getName()!=null)
			ycxm.setName(user.getName());
		}
		
		this.save(ycxm);
		
	}
	
	
	public int getAges(Tjxx tjx){
		if(tjx.getTjAges()<-1){
			String carid=tjx.getCardId().trim();
			String yy=carid.substring(6,8);
			//SimpleDateFormat sfmt = new SimpleDateFormat("yyyyMMdd");
			 try {
				// Date brithDay=sfmt.parse(carid);
				 yy="19"+yy;
				 int tt=Integer.valueOf(yy);
				 Date today = tjx.getTjDate();
				  
				 int age= today.getYear()+1900 - tt;
				 return age;
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 40;
			}
			
		}else{
			return tjx.getTjAges();
		}
	}
	
	//对tjx 体检结果进行语义转换
	public double yyzh(Tjxx tjx,JkTjxm tjxm){
		
		//String esc=tjxm.getEscValue();
		String inesc=tjx.getTjjg().trim().toLowerCase();
		
		double jg=0.0;
		
		if(inesc.contains("未见")||inesc.contains("正常")||inesc.contains("无")){
			jg=-1.0;
			return jg;
		}if(inesc.contains("未查")){
			jg=0.0;
			return jg;
		}if(inesc.contains("-")){
			jg=count(inesc,"-")*-1.0;
			return jg;
		}if(inesc.contains("+")){
			jg=count(inesc,"+")*1.0;
			return jg;
		}
		
		try{
			jg=Double.valueOf(inesc);
		}catch(Exception ex){
			return 0.0;
		}
		return jg;
	}
	
	//统计字符
	public  Integer count(String string,String a) { 
		 int number = 0; 
		 int len = a.length(); 
		 int index = 0; 
		 for(int i=0; i<string.length();i=len+index) { 
			 if((index = string.indexOf(a, i)) > -1) 
				 number ++; 
			 else 
				 break; 
		 } 
		 return number;
	}

	@Override
	public String delByHql(String query) {
		String hql=" DELETE FROM JkYcxm o WHERE 1=1 "+query;
		this.update(hql);
		
		return "del OK!";
	}
	

}
