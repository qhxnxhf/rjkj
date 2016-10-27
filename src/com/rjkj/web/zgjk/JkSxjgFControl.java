package com.rjkj.web.zgjk;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.PageOrientation;
import jxl.format.PaperSize;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rjkj.dao.JkSxjgDao;
import com.rjkj.entities.JkSxjg;
import com.rjkj.util.web.JsonUtil;
import com.rjkj.util.web.Page;


@Controller
@RequestMapping("/zgjk/sxjgf")
public class JkSxjgFControl {
	
	private static final Log log = LogFactory.getLog(JkSxjgFControl.class);  
	//protected ErrMg error; 
	
	@Autowired
	private JkSxjgDao sxService;
	
	
	@RequestMapping(value="/list", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String getTreeList(HttpServletRequest request){
		String cId = request.getParameter("carId");
		String ucid = request.getParameter("ucid");
		String tjlb= request.getParameter("tjlb");;
		String id = request.getParameter("tjbm");
		String name = request.getParameter("uname");		
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		
		String tj1= request.getParameter("tj1");
		String tj2= request.getParameter("tj2");
		String tj3= request.getParameter("tj3");
		
		
		String sort= request.getParameter("sort");;
		String direction= request.getParameter("direction");
		String pageNo= request.getParameter("pager.pageNo");
		String pageSize= request.getParameter("pager.pageSize");
		Page page=new Page();
		page.setPageNo(Integer.valueOf(pageNo));
		page.setPageSize(Integer.valueOf(pageSize));
		
		String query=" WHERE 1=1 ";
		
		if(StringUtils.isNotBlank(id)){
			query=query+" AND deptId="+id;
		}
		if(StringUtils.isNotBlank(tjlb)){
			query=query+" AND tjType ='"+tjlb+"'";
		}
		
		if(StringUtils.isNotBlank(name)){
			query=query+" AND name LIKE '%"+name+"%'";
		}
		
		if(StringUtils.isNotBlank(cId)){
			query=query+" AND cardId ='"+cId+"'";
		}else{
			if(StringUtils.isNotBlank(ucid)){
				query=query+" AND cardId ='"+ucid+"'";
			}
			
		}
		
		
		
		if(beginTime!=null&&!beginTime.equals("")&&endTime!=null&&!endTime.equals("")){
			query=query+" AND  tjDate between '"+beginTime+" 00:00:00' and '"+endTime+" 23:59:59'" ;
			//query=query+"  AND o.pubDate between to_date('"+beginTime+"','yyyy-mm-dd') and to_date('"+endTime+"','yyyy-mm-dd') " ;
			
		}
		
		if(StringUtils.isNotBlank(tj1)&&tj1.equals("1")){
			query=query+" AND o.tongji1=1";
		}
		if(StringUtils.isNotBlank(tj2)&&tj2.equals("1")){
			query=query+" AND o.tongji2=1";
		}
		if(StringUtils.isNotBlank(tj3)&&tj3.equals("1")){
			query=query+" AND o.tongji3=1";
		}
		
		JSONObject json = new JSONObject();
		JSONArray jsonArray;
		List<JkSxjg> list;
		list=this.sxService.findAll(query, page);
		String[] excludes={"children","users","news","docs"};
		JsonConfig config =JsonUtil.configJson(excludes, "yyyy-MM-dd HH:mm:ss");
		jsonArray=JSONArray.fromObject(list, config);
		
		
		json.put("pager.pageNo", page.getPageNo());
		json.put("pager.totalRows", page.getTotalRows());
		json.put("rows", jsonArray);
		json.put("sort", sort);
		json.put("direction", direction);
		
		String result=json.toString();
		return result;
	}
	
	@RequestMapping(value="/excel", method={RequestMethod.GET, RequestMethod.POST})
	public void  getExcel(HttpServletRequest request, HttpServletResponse response){
		
		String cId = request.getParameter("carId");
		String tjlb= request.getParameter("tjlb");;
		String id = request.getParameter("tjbm");
		String name = request.getParameter("uname");	
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		
		String tj1= request.getParameter("tj1");
		String tj2= request.getParameter("tj2");
		String tj3= request.getParameter("tj3");
		
		String query=" WHERE 1=1 ";
		
		if(StringUtils.isNotBlank(id)){
			query=query+" AND deptId="+id;
		}
		if(StringUtils.isNotBlank(tjlb)){
			query=query+" AND tjType ='"+tjlb+"'";
		}
		
		if(StringUtils.isNotBlank(name)){
			query=query+" AND name LIKE '%"+name+"%'";
		}
		
		if(StringUtils.isNotBlank(cId)){
			query=query+" AND cardId ='"+cId+"'";
		}
		if(beginTime!=null&&!beginTime.equals("")&&endTime!=null&&!endTime.equals("")){
			query=query+" AND  tjDate between '"+beginTime+" 00:00:00' and '"+endTime+" 23:59:59'" ;
			//query=query+"  AND o.pubDate between to_date('"+beginTime+"','yyyy-mm-dd') and to_date('"+endTime+"','yyyy-mm-dd') " ;
			
		}
		
		if(StringUtils.isNotBlank(tj1)&&tj1.equals("1")){
			query=query+" AND o.tongji1=1";
		}
		if(StringUtils.isNotBlank(tj2)&&tj2.equals("1")){
			query=query+" AND o.tongji2=1";
		}
		if(StringUtils.isNotBlank(tj3)&&tj3.equals("1")){
			query=query+" AND o.tongji3=1";
		}
		
		List<JkSxjg> list=this.sxService.findAll(JkSxjg.class,query);
		
		
		
		if(list!=null&&list.size()>0){
			
			Object[] data=list.toArray();
			commonExport(response,data);
		}
		
		
	}
	
	
	
	//生成Excel文件
	private void commonExport(HttpServletResponse response, Object[] data) {
	       if (data == null)
	            return;
	        WritableWorkbook wwb = null;
	        try {
	        	//String filename=doc.getDocName()+"."+doc.getSuffix();
	    	    //设置文件输出类型
	        	response.setHeader("pragma", "no-cache");
	    	    response.setContentType("application/octet-stream");  
	    	    //response.setContentType(doc.getMimeType());
	    	    response.setHeader("Content-disposition", "attachment; filename="  
	    	       + new String(("职工健康统计表.xls").getBytes("GBK"), "iso-8859-1")); 
	    	    //设置输出长度
	    	    //response.setHeader("Content-Length", String.valueOf(fileLength));  
	    	    //获取输入流
	        	//ResponseStream stream
	        	//BufferedOutputStream stream= new BufferedOutputStream(response.getOutputStream()); 
	            wwb = Workbook.createWorkbook(response.getOutputStream());
	            WritableSheet ws = wwb.createSheet("DataGrid数据", 0);
	            ws.getSettings().setPaperSize(PaperSize.A4);
	            ws.getSettings().setHorizontalCentre(true);
	            ws.getSettings().setOrientation(PageOrientation.LANDSCAPE);
	               
	            ws.getSettings().setBottomMargin(0.5);
	            ws.getSettings().setTopMargin(0.5);
	            ws.getSettings().setRightMargin(0.2);
	            ws.getSettings().setLeftMargin(0.2);
	            
	           // ws.getSettings().setDefaultRowHeight(5);
	            //ws.getSettings().setDefaultRowHeight(100);
	           
	           // ws.getSettings().setVerticalCentre(true);
	           
	          //  ws.getSettings().setFitToPages(true);
	           
	           
	            ws.setColumnView(0, 10);
	            ws.setColumnView(1, 10);
	            ws.setColumnView(2, 10);
	            ws.setColumnView(3, 20);
	            ws.setColumnView(4, 10);
	            ws.setColumnView(5, 10);
	            ws.setColumnView(6, 10);
	            ws.setColumnView(7, 10);
	            ws.setColumnView(8, 10);
	            ws.setColumnView(9, 10);
	            ws.setColumnView(10, 10);
	            ws.setColumnView(11, 10);
	            ws.setColumnView(12, 10);
	            ws.setColumnView(13, 10);
	            ws.setColumnView(14, 10);
	            ws.setColumnView(15, 10);
	            ws.setColumnView(16, 10);
	           
	           
	              
	            jxl.write.WritableFont wfont= new WritableFont(WritableFont.createFont("宋体"),18,WritableFont.BOLD); 
	            WritableCellFormat title = new WritableCellFormat(wfont);     
	            // 设置居中     
	            title.setAlignment(Alignment.CENTRE);     
	            title.setVerticalAlignment(VerticalAlignment.CENTRE);
	            jxl.write.WritableFont wfont2= new WritableFont(WritableFont.createFont("宋体"),10,WritableFont.BOLD); 
	            WritableCellFormat items = new WritableCellFormat(wfont2);     
	            // 设置居中     
	            items.setAlignment(Alignment.CENTRE); 
	           
	            items.setVerticalAlignment(VerticalAlignment.CENTRE);
	            // 设置边框线     
	            items.setBorder(Border.ALL, jxl.format.BorderLineStyle.THIN);     
	            // 设置单元格的背景颜色     
	            items.setBackground(jxl.format.Colour.WHITE); 
	            
	            jxl.write.WritableFont wfont3= new WritableFont(WritableFont.createFont("宋体"),10); 
	            WritableCellFormat cellstring = new WritableCellFormat(wfont3);     
	            // 设置居中     
	            cellstring.setAlignment(Alignment.LEFT);  
	            cellstring.setVerticalAlignment(VerticalAlignment.CENTRE);
	           
	            // 设置边框线     
	            cellstring.setBorder(Border.ALL, BorderLineStyle.THIN);     
	            // 设置单元格的背景颜色     
	            cellstring.setBackground(jxl.format.Colour.WHITE); 
	            
	            
	            WritableCellFormat cellint = new WritableCellFormat(wfont3);     
	            // 设置居中     
	            cellint.setAlignment(Alignment.RIGHT);  
	            cellint.setVerticalAlignment(VerticalAlignment.CENTRE);
	            // 设置边框线     
	            cellint.setBorder(Border.ALL, BorderLineStyle.THIN);     
	            // 设置单元格的背景颜色     
	            cellint.setBackground(jxl.format.Colour.WHITE); 
	            
	            jxl.write.NumberFormat nf = new jxl.write.NumberFormat("#,##0.00");     
	            jxl.write.WritableCellFormat celldouble = new jxl.write.WritableCellFormat(nf);   
	            celldouble.setBorder(Border.ALL, BorderLineStyle.THIN);
	            celldouble.setAlignment(Alignment.RIGHT);
	            celldouble.setVerticalAlignment(VerticalAlignment.CENTRE);
	            
	            jxl.write.DateFormat df = new jxl.write.DateFormat("yyyy-MM-dd");
	            jxl.write.WritableCellFormat celldate = new jxl.write.WritableCellFormat(df);
	            celldate.setBorder(Border.ALL, BorderLineStyle.THIN);
	            celldate.setAlignment(Alignment.CENTRE);
	            celldate.setVerticalAlignment(VerticalAlignment.CENTRE);
	            
	            ws.setRowView(0, 500);
	            ws.mergeCells(0,0,17,0);     
	            Label labelItem = new Label(0,0,"职工健康统计表",title);     
	            ws.addCell(labelItem);
	            
	            
	            ws.setRowView(1, 300);
	            ws.mergeCells(0,1,3,1);
	            labelItem = new Label(0,1,"制表人：");     
	            ws.addCell(labelItem);
	           
	           
	            
	           SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");     
	           String newdate1 = sdf1.format(new Date()); 
	           ws.mergeCells(16,1,17,1);
	           labelItem = new Label(16,1,"日期："+newdate1);    
	           ws.addCell(labelItem);
	            
	           labelItem = new Label(0, 2, "身份证", items);
               ws.addCell(labelItem);
               labelItem = new Label(1, 2, "姓名", items);
               ws.addCell(labelItem);
               labelItem = new Label(2, 2, "性别", items);
               ws.addCell(labelItem);
               labelItem = new Label(3, 2, "体检类别", items);
               ws.addCell(labelItem);
               
               labelItem = new Label(4, 2, "体检日期", items);
               ws.addCell(labelItem);
               
               
               labelItem = new Label(5, 2, "一类", items);
               ws.addCell(labelItem);
               labelItem = new Label(6, 2, "二类", items);
               ws.addCell(labelItem);
               labelItem = new Label(7, 2, "三类", items);
               ws.addCell(labelItem);
               labelItem = new Label(8, 2, "年龄", items);
               ws.addCell(labelItem);
               
               labelItem = new Label(9, 2, "体重指数", items);
               ws.addCell(labelItem);
               
               labelItem = new Label(10, 2, "甘油三酯", items);
               ws.addCell(labelItem);
               
               labelItem = new Label(11, 2, "总胆固醇", items);
               ws.addCell(labelItem);
               
               labelItem = new Label(12, 2, "高密度脂蛋白", items);
               ws.addCell(labelItem);
               
               labelItem = new Label(13, 2, "低密度脂蛋白", items);
               ws.addCell(labelItem);
               
               labelItem = new Label(14, 2, "空腹血糖", items);
               ws.addCell(labelItem);
               
               labelItem = new Label(15, 2, "舒张压", items);
               ws.addCell(labelItem);
               
               labelItem = new Label(16, 2, "FEV1%", items);
               ws.addCell(labelItem);
               
               labelItem = new Label(17, 2, "实测值/预期值(%)", items);
               ws.addCell(labelItem);
               
              
               
               
               
               
	          // int xh=0;
	            // 写入数据
	            for (int i = 0; i < data.length; i++) {
	            	
	            	ws.setRowView(i+2, 300);
	            	
	            	JkSxjg s = (JkSxjg)data[i];
	            	int y=i+3;
	            	
	            	
	            		
	            		//xh=0;
	            	
	            		//xh=xh+1;
	            		labelItem = new Label(0, y, s.getCardId()+"",cellstring);
	            		ws.addCell(labelItem);
	            		
	            		 if(s.getName()!=null){
	     					labelItem = new Label(1, y, s.getName(),cellstring);
	     					ws.addCell(labelItem);
	     				
	     				}else{
	     					labelItem = new Label(1, y, "",cellstring);
	     					ws.addCell(labelItem);
	     				}
	            		 
	            		 if(s.getSex()!=null){
	      					labelItem = new Label(2, y, s.getSex(),cellstring);
	      					ws.addCell(labelItem);
	      				
	      				}else{
	      					labelItem = new Label(2, y, "",cellstring);
	      					ws.addCell(labelItem);
	      				}
	            		
	            		 
	            		 if(s.getTjType()!=null){
		       					labelItem = new Label(3, y, s.getTjType(),cellstring);
		       					ws.addCell(labelItem);
		       				
		       				}else{
		       					labelItem = new Label(3, y, "",cellstring);
		       					ws.addCell(labelItem);
		       				}
	            		 
	            		 if(s.getTjDate()!=null){
		     					jxl.write.DateTime labelDT = new jxl.write.DateTime(4, y, s.getTjDate() ,celldate); 
		     					ws.addCell(labelDT);
		     				
		     			}else{
		     					labelItem = new Label(4, y, "",cellstring);
		     					ws.addCell(labelItem);
		     				}
	            		
	       				labelItem = new Label(5, y, this.getType(s.getTongji1()),cellstring);
	       				ws.addCell(labelItem);
	       				
	       				labelItem = new Label(6, y, this.getType(s.getTongji2()),cellstring);
	       				ws.addCell(labelItem);
	       					
	       				labelItem = new Label(7, y, this.getType(s.getTongji2()),cellstring);
	       				ws.addCell(labelItem);
	            		 
	            		
	       				if(s.getAge()!=null){
	       					labelItem = new Label(8, y, s.getAge().toString(),cellstring);
	       					ws.addCell(labelItem);
	       				
	       				}else{
	       					labelItem = new Label(8, y, "",cellstring);
	       					ws.addCell(labelItem);
	       				}
	       				
	       				
	       				if(s.getBmi()!=null){
	       					labelItem = new Label(9, y, s.getBmi().toString(),cellstring);
	       					ws.addCell(labelItem);
	       				
	       				}else{
	       					labelItem = new Label(9, y, "",cellstring);
	       					ws.addCell(labelItem);
	       				}
	       				
	       				
	       				if(s.getGysz()!=null){
	       					labelItem = new Label(10, y, s.getGysz().toString(),cellstring);
	       					ws.addCell(labelItem);
	       				
	       				}else{
	       					labelItem = new Label(10, y, "",cellstring);
	       					ws.addCell(labelItem);
	       				}
	       				
	       				if(s.getZongdan()!=null){
	       					labelItem = new Label(11, y, s.getZongdan().toString(),cellstring);
	       					ws.addCell(labelItem);
	       				
	       				}else{
	       					labelItem = new Label(11, y, "",cellstring);
	       					ws.addCell(labelItem);
	       				}
	       				
	       				
	       				if(s.getGaomi()!=null){
	       					labelItem = new Label(12, y, s.getGaomi().toString(),cellstring);
	       					ws.addCell(labelItem);
	       				
	       				}else{
	       					labelItem = new Label(12, y, "",cellstring);
	       					ws.addCell(labelItem);
	       				}
	       				
	       				if(s.getDimi()!=null){
	       					labelItem = new Label(13, y, s.getDimi().toString(),cellstring);
	       					ws.addCell(labelItem);
	       				
	       				}else{
	       					labelItem = new Label(13, y, "",cellstring);
	       					ws.addCell(labelItem);
	       				}
	       				
	       				
	       				if(s.getKfxt()!=null){
	       					labelItem = new Label(14, y, s.getKfxt().toString(),cellstring);
	       					ws.addCell(labelItem);
	       				
	       				}else{
	       					labelItem = new Label(14, y, "",cellstring);
	       					ws.addCell(labelItem);
	       				}
	       				
	       				
	       				if(s.getShzhya()!=null){
	       					labelItem = new Label(15, y, s.getShzhya().toString(),cellstring);
	       					ws.addCell(labelItem);
	       				
	       				}else{
	       					labelItem = new Label(15, y, "",cellstring);
	       					ws.addCell(labelItem);
	       				}
	       				
	       				if(s.getFev1()!=null){
	       					labelItem = new Label(16, y, s.getFev1().toString(),cellstring);
	       					ws.addCell(labelItem);
	       				
	       				}else{
	       					labelItem = new Label(16, y, "",cellstring);
	       					ws.addCell(labelItem);
	       				}
	       				
	       				if(s.getFev1fvc()!=null){
	       					labelItem = new Label(17, y, s.getFev1fvc().toString(),cellstring);
	       					ws.addCell(labelItem);
	       				
	       				}else{
	       					labelItem = new Label(17, y, "",cellstring);
	       					ws.addCell(labelItem);
	       				}
	            	
	            }
	            
	           
	            wwb.write();
	            wwb.close();
	            response.flushBuffer();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (RowsExceededException e) {
	            e.printStackTrace();
	        } catch (WriteException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    
	    String getType(Long tj){
	    	
	    	if((tj!=null&&tj!=0)){
		    	return "是";
	    	}
	    		
	    	return "";
	    	
	    }
	    
	  
}
