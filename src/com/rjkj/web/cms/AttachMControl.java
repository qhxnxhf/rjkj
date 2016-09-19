package com.rjkj.web.cms;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.rjkj.dao.LmDao;
import com.rjkj.dao.NewsAttachDao;
import com.rjkj.dao.NewsDao;
import com.rjkj.entities.News;
import com.rjkj.entities.NewsAttach;
import com.rjkj.entities.NewsLm;
import com.rjkj.entities.Organize;
import com.rjkj.entities.User;
import com.rjkj.service.PropertiesService;
import com.rjkj.util.web.FileUtils;
import com.rjkj.util.web.JsonUtil;
import com.rjkj.util.web.Page;
import com.rjkj.web.interceptor.CsrfTokenUtils;




@Controller
@RequestMapping("/cms/attach")
public class AttachMControl {
	
	@Resource(name = "PropertiesService")
	private PropertiesService propertiesService;
	
	@Autowired
	private NewsAttachDao attService;
	
	@Autowired
	private LmDao lmService;
	
	@Autowired
	private NewsDao newsService;
	
	private static final String MSG = "gz/MsgPage";
	
	private static final String NEWSDISPLAY="cms/attach/NewsDisplay";
	
	//修改
		@RequiresPermissions("NewsM:edit")
		@RequestMapping(value="/preEdit", method={RequestMethod.GET})
		public String preEdit(Map<String, Object> map ,HttpServletRequest request ){
			HttpSession session = request.getSession(true);  
	        User user=(User)session.getAttribute("LoginUser"); 
	        Organize org=user.getOrg();
			
			String id = request.getParameter("newsId");
			String url = request.getParameter("url");
			News doc=this.newsService.findById(News.class,Long.valueOf(id));
			String status=doc.getStatus();
			
			NewsLm porg=doc.getLm();
			
			String orgids=porg.getOrgIdsWj();
			/**
			if(include(orgids,org.getId())){
				if(status!=null&&!status.equals("")){
					if(status.equals("1")){
						map.put("doc", doc);
						return url;
					}else{
						map.put("msg", "已经提交不能修改了");
						return MSG;
					}
				}
			}
			map.put("msg", "没有操作权限！");
			return MSG;
			**/
			request.setAttribute(CsrfTokenUtils.CSRF_TOKEN_NAME, CsrfTokenUtils.getTokenForSession(request.getSession()));
			
			map.put("news", doc);
			return url;
			
		}
	
	@RequiresPermissions("NewsM:view")
	@RequestMapping(value="/table", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String getTable(HttpServletRequest request){
		
        String newsid= request.getParameter("newsId");
		String sort= request.getParameter("sort");
		String direction= request.getParameter("direction");
		String pageNo= request.getParameter("pager.pageNo");
		String pageSize= request.getParameter("pager.pageSize");
		
		Page page=new Page();
		page.setPageNo(Integer.valueOf(pageNo));
		page.setPageSize(Integer.valueOf(pageSize));
		
		String query=" WHERE 1=1 AND o.news.id="+newsid;
		query=query+" ORDER BY o."+sort+" "+direction;
		
		List<NewsAttach> list=this.attService.findAll(query, page);
		
		String[] excludes={"children","news","attach","docs","users","receiver","comment"};
		JsonConfig config =JsonUtil.configJson(excludes, "yyyy/MM/dd HH:mm:ss");
		JSONArray jsonArray=JSONArray.fromObject(list, config);
		
		JSONObject json = new JSONObject();
		json.put("pager.pageNo", page.getPageNo());
		json.put("pager.totalRows", page.getTotalRows());
		json.put("rows", jsonArray);
		json.put("sort", sort);
		json.put("direction", direction);
		
		String result=json.toString();
		return result;
	}
	
	
	//根据图片所属新闻Id查询
	@RequiresPermissions("NewsM:attach")
	@RequestMapping(value="/imgList", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String getImgList(HttpServletRequest request){
		JSONObject json = new JSONObject();
		JSONArray jsonArray = new JSONArray();    
		String newid= request.getParameter("newid");
		String query=" WHERE o.news.id="+newid;
		List<NewsAttach> list=this.attService.findAll(NewsAttach.class,query);
		if(list!=null&&list.size()>0){
    		for (int i = 0; i <list.size(); i++) {
    			NewsAttach org=list.get(i);
    			JSONObject json2 = new JSONObject();
    			json2.put("value", org.getId());
    			json2.put("key", org.getTitle());
    			json2.put("path", org.getFilePath());
    			json2.put("brief", org.getBrief());
    			json2.put("orderNum", org.getOrderNum());
    			json2.put("suffix", org.getSuffix());
    			json2.put("fileSize", org.getFileSize());
    		    jsonArray.add(json2);
    		}
    	}
		json.put("list", jsonArray);
		String result=json.toString();
		return result;
	    
	}
	
	//根据图片类别查询
	@RequiresPermissions("NewsM:attach")
	@RequestMapping(value="/imgsByType", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String getImgsByType(HttpServletRequest request){
		JSONObject json = new JSONObject();
		JSONArray jsonArray = new JSONArray();    
		String typeid= request.getParameter("typeid");
		String query=" WHERE o.attType="+typeid;
		List<NewsAttach> list=this.attService.findAll(NewsAttach.class,query);
		if(list!=null&&list.size()>0){
    		for (int i = 0; i <list.size(); i++) {
    			NewsAttach org=list.get(i);
    			JSONObject json2 = new JSONObject();
    			json2.put("value", org.getId());
    			json2.put("key", org.getTitle());
    			json2.put("path", org.getFilePath());
    			json2.put("brief", org.getBrief());
    			json2.put("orderNum", org.getOrderNum());
    			json2.put("suffix", org.getSuffix());
    			json2.put("fileSize", org.getFileSize());
    		    jsonArray.add(json2);
    		}
    	}
		json.put("list", jsonArray);
		String result=json.toString();
		return result;
	    
	}
	
	
	
	@RequiresPermissions("NewsM:attach")
	@RequestMapping(value = "/saveAdd", method = RequestMethod.POST)
	public @ResponseBody String saveAdd(Map<String, Object> map,HttpServletRequest request, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		if(!CsrfTokenUtils.validCsrfToken(request)){
			json.put("status", "false");
			json.put("message", "非法操作!");
			String result=json.toString();
			return result;
		}
		
		HttpSession session = request.getSession(true);  
        User user=(User)session.getAttribute("LoginUser"); 
        String id= request.getParameter("id");
        String title= request.getParameter("title");
        String brief= request.getParameter("brief");
        String orderNum= request.getParameter("orderNum");
        
		try {
			NewsAttach doc=this.attService.findById(NewsAttach.class, id);
			//out = response.getWriter();
			doc.setCreateDate(new Date());
			doc.setTitle(title);
			doc.setBrief(brief);
			doc.setOrderNum(Integer.valueOf(orderNum));
			this.attService.update(doc);
			json.put("status", "true");
			json.put("message", "修改成功");
			map.put("doc", doc);
			
		} catch (Exception e) {
			//e.printStackTrace();
			json.put("status", "false");
			json.put("message", e.toString());
		}
		 
		String result=json.toString();
		return result;
		 
	}
	
	
	@RequiresPermissions("NewsM:attach")
	@RequestMapping(value="/setIcon", method={RequestMethod.POST})
	public @ResponseBody String setIcon(HttpServletRequest request  ,Map<String, Object> map ){
		JSONObject json = new JSONObject();
		if(!CsrfTokenUtils.validCsrfToken(request)){
			json.put("status", "false");
			json.put("message", "非法操作!");
			String result=json.toString();
			return result;
		}
		HttpSession session = request.getSession(true);  
        User user=(User)session.getAttribute("LoginUser"); 
        Organize org=user.getOrg();
		
		String id = request.getParameter("attId");
		
		if (id != null && id!="") {
			try{
				
				NewsAttach doc=this.attService.findById(NewsAttach.class, id);
				doc.setStatus("2");
				News news=doc.getNews();
				//News news=this.newsService.findById(News.class, doc.getNews().getId());
				news.setIcoPath(doc.getId());
				this.newsService.update(news);
				this.attService.update(doc);
				json.put("status", "true");
				json.put("message", "OK!");
				
			}catch(Exception e){
				json.put("status", "false");
				json.put("message", e.toString());
			}
	    }else{
	    	json.put("status", "false");
			json.put("message", "请选择要图片!");
	    }
		
		String result=json.toString();
		return result;
	}
	
	
	@RequiresPermissions("NewsM:attach")
	@RequestMapping(value="/setLmIcon", method={RequestMethod.POST})
	public @ResponseBody String setLmIcon(HttpServletRequest request  ,Map<String, Object> map ){
		JSONObject json = new JSONObject();
		if(!CsrfTokenUtils.validCsrfToken(request)){
			json.put("status", "false");
			json.put("message", "非法操作!");
			String result=json.toString();
			return result;
		}
		HttpSession session = request.getSession(true);  
        User user=(User)session.getAttribute("LoginUser"); 
        Organize org=user.getOrg();
		
		String id = request.getParameter("attId");
		
		if (id != null && id!="") {
			try{
				
				NewsAttach doc=this.attService.findById(NewsAttach.class, id);
				doc.setStatus("3");
				News news=doc.getNews();
				NewsLm lm=news.getLm();
				lm.setLmIcon(doc.getId());
				this.lmService.update(lm);
				//News news=this.newsService.findById(News.class, doc.getNews().getId());
				//news.setIcoPath(doc.getId());
				//this.newsService.update(news);
				this.lmService.update(lm);
				this.attService.update(doc);
				json.put("status", "true");
				json.put("message", "OK!");
				
			}catch(Exception e){
				json.put("status", "false");
				json.put("message", e.toString());
			}
	    }else{
	    	json.put("status", "false");
			json.put("message", "请选择要图片!");
	    }
		
		String result=json.toString();
		return result;
	}
	
	
	
	@RequiresPermissions("NewsM:attach")
	@RequestMapping(value="/del", method={RequestMethod.POST})
	public @ResponseBody String del(HttpServletRequest request  ,Map<String, Object> map ){
		JSONObject json = new JSONObject();
		if(!CsrfTokenUtils.validCsrfToken(request)){
			json.put("status", "false");
			json.put("message", "非法操作!");
			String result=json.toString();
			return result;
		}
		HttpSession session = request.getSession(true);  
        User user=(User)session.getAttribute("LoginUser"); 
        Organize org=user.getOrg();
        String rootPath = request.getServletContext().getInitParameter("rootPath");
       
		String ids = request.getParameter("attId");
		
		if (ids != null && ids!="") {
			try{
				NewsAttach doc=this.attService.findById(NewsAttach.class, ids);
				String file=rootPath+doc.getFilePath();
				//this.attService.delIds(NewsAttach.class, "'"+ids+"'");
				this.attService.delFileByIds(ids,rootPath);
				//File docfile=new File(file);
				//if(docfile.exists())
				//	 docfile.delete();
				json.put("status", "true");
				json.put("message", "删除成功!");
				
			}catch(Exception e){
				json.put("status", "false");
				json.put("message", e.toString());
			}
	    }else{
	    	json.put("status", "false");
			json.put("message", "请选择要删除的节点!");
	    }
		
		String result=json.toString();
		return result;
	}
	
	private boolean include(String uslist, Long id) {
		String [] usls=uslist.split(",");
		String ids=id.toString();
		for(int i=0;i<usls.length;i++){
			if(usls[i].equals(ids))
				return true;
		}
		return false;
	}
	
	
	
	
	
	//保存上传文件，此访求在本项目中没有使用
	public String saveFile(NewsAttach doc,MultipartFile file,HttpServletRequest request){
				
				String docsPath = request.getServletContext().getInitParameter("docsPath");
				
				//String category = GzglDoc.class.getSimpleName();
				
				String indexPath = request.getServletContext().getInitParameter("indexPath");
				
				//允许上传的文件扩展名
				//MimeType mmtype=MimeType.getMimeType();
				//最大文件大小
				long maxSize = 10*1024*1024;
				//按日期生成文件路径
				Date date=new Date();
				Calendar c1 = Calendar.getInstance();
			    c1.setTime(date);
			    int year=c1.get(Calendar.YEAR);
			    int month=c1.get(Calendar.MONTH)+1;
			    //int day=c1.get(c1.DAY_OF_MONTH);
				String subPath= "/"+year+"/"+month;
			    String filePath = docsPath + subPath;
				File docsFile = new File(filePath); // 指定上传文件的位置
				if (!docsFile.exists() || docsFile == null) { // 判断指定路径dir是否存在，不存在则创建路径
					docsFile.mkdirs();
				}
				
				//检查目录写权限
				if(!docsFile.canWrite()){
					return "上传目录没有写权限。";
				}
				
				try {
					if(!file.isEmpty()){
						//String origName=file.getName();
						String origName=file.getOriginalFilename();
						String mimetype=file.getContentType();
						String suffix=FileUtils.getFileExt(origName);
						String reName =doc.getId()+"."+suffix;
						subPath=subPath+"/"+reName;
						Long filesize=file.getSize();
						
						//检查文件大小
						if(filesize > maxSize){
							return "上传文件大小超过限制:"+(maxSize/1024*1024)+"MB";
						}
						
						//将原来的文件删除
						String oldfilePath = docsPath+doc.getFilePath();
						File oldDocFile = new File(oldfilePath);
						if(oldDocFile.exists()){
							oldDocFile.delete();
						}
						
						//检查扩展名
						if(!FileUtils.ifInclude(suffix)){
							return  suffix+":上传文件扩展名是不允许的扩展名";
						}
						
						file.transferTo(new File(docsFile,reName));
						doc.setFileOrigName(origName);
						doc.setFilePath(subPath);
						doc.setFileSize(filesize);
						doc.setSuffix(suffix);
						doc.setMimeType(mimetype);
						doc.setStatus("10");
						doc.setCreateDate(new Date());
						return "ok";
					}else{
						return null;
					}
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
		}
	
	//文件下载方法
	@RequestMapping(value = "/flv/{docId}")
	public void getFlv(@PathVariable("docId") String docId,HttpServletRequest request, HttpServletResponse response) {
	        try {
	        	NewsAttach doc=this.attService.findById(NewsAttach.class, docId);
	        	if(doc!=null)
	           this.getFlvStream(request, response, doc);
	        } catch (Exception e) {
	            
	        }
	    }
	
	//文件下载方法
	@RequestMapping(value = "/download/{docId}")
	public void exportModel(@PathVariable("docId") String docId,HttpServletRequest request, HttpServletResponse response) {
		        try {
		        	NewsAttach doc=this.attService.findById(NewsAttach.class, docId);
		        	if(doc!=null)
		           this.download(request, response, doc);
		        } catch (Exception e) {
		            
		        }
	}
	
	
	
	
	
	//文件下载方法
		public void download(HttpServletRequest request,  
			      HttpServletResponse response, NewsAttach doc) throws Exception {  
			    
				String rootPath = request.getServletContext().getInitParameter("rootPath");
				//if(rootPath.equals("upload")){
				//	rootPath=request.getServletContext().getRealPath("/uploda");
				//}
				
			    request.setCharacterEncoding("UTF-8");  
			    BufferedInputStream bis = null;  
			    BufferedOutputStream bos = null;  
			    
			    //获取下载文件
			    String downLoadPath = rootPath+doc.getFilePath();  
			    //downLoadPath=request.getServletContext().getRealPath(downLoadPath);
			  
			    //获取文件的长度
			    //long fileLength = doc.getFileSize();  
			    String filename=doc.getFileOrigName()+"."+doc.getSuffix();
			    //设置文件输出类型
			    response.setContentType("application/octet-stream");  
			    //response.setContentType(doc.getMimeType());
			    response.setHeader("Content-disposition", "attachment; filename="  
			        + new String(filename.getBytes("GBK"), "iso-8859-1")); 
			    //获取输入流
			    FileInputStream fileinput=new FileInputStream(downLoadPath);
			    //设置输出长度
			    response.setHeader("Content-Length", String.valueOf(fileinput.available())); 
			    
			    bis = new BufferedInputStream(fileinput);  
			    //输出流
			    bos = new BufferedOutputStream(response.getOutputStream());  
			    byte[] buff = new byte[2048];  
			    int bytesRead;  
			    while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {  
			      bos.write(buff, 0, bytesRead);  
			    }  
			    //关闭流
			    
			    bis.close();  
			    bos.close();  
			    fileinput.close();
			}  
	//文件下载方法
	public void getFlvStream(HttpServletRequest req,  
			 	HttpServletResponse resp, NewsAttach doc) throws Exception {  
		String rootPath = req.getServletContext().getInitParameter("rootPath");
		String flvPath  = rootPath+doc.getFilePath();  
		  resp.reset();
	      resp.setContentType("Video/x-flv");
	      String target = req.getParameter("target");  //接收参数，为字节数
	        int targetInt = 0;
	        System.out.println("Target:" + target);
	        System.out.println("Target:" + req.getServletPath());
	        //String flvPath = req.getSession().getServletContext().getRealPath(req.getServletPath());
	        System.out.println(flvPath);
	        RandomAccessFile raf = null;
	        int totalByte = 0;
	        try{
	        	 //获取输入流
			    if(rootPath.equals("upload"))
			    	flvPath=req.getServletContext().getRealPath(flvPath);
			    
	            raf = new RandomAccessFile(flvPath, "r");
	            totalByte = (int)raf.length();
	            if (target != null && !"".equals(target)) {
	                try {
	                    targetInt = Integer.parseInt(target);
	            byte[] headData = new byte[]{'F','L','V',1,1,0,0,0,9,0,0,0,9}; //拖动时间轴后的response中头信息需写入该字节 
	                    resp.getOutputStream().write(headData);
	                    resp.setContentLength(totalByte-targetInt+13);
	                } catch (NumberFormatException e) {
	                    targetInt = 0;
	                }
	            } else {
	                resp.setContentLength(totalByte-targetInt);
	            }
	            raf.skipBytes(targetInt);//跳过时间轴前面的字节;
	            byte[] b = new byte[4096];
	            while(raf.read(b) != -1) {
	                resp.getOutputStream().write(b);
	            }
	            resp.getOutputStream().flush();
	        } catch (Exception e) {
	            String simplename = e.getClass().getSimpleName();
	            if(!"ClientAbortException".equals(simplename)){
	                e.printStackTrace();
	            }//web端拖动时间轴总有连接被重置的异常，暂时还不知如何解决，可以此方式不输出异常
	        } finally {
	            if(raf != null){
	                raf.close();
	            }
	        }
	    }
			
		
		
		//文件删除方法
		public void delfiles(HttpServletRequest request,String ids) throws Exception {  
			String rootPath = request.getServletContext().getInitParameter("rootPath");
			   if(ids!=null&&!ids.equals("")){
				   String [] id=ids.split(",");
				   for(int i=0;i<id.length;i++){
					   NewsAttach doc=this.attService.findById(NewsAttach.class, id[i]);
					   String docPath = rootPath+doc.getFilePath();  
					   File df=new File(docPath);
					   if(df.exists()){
						   df.delete();
					   }
				   }
			   }
			   
		}  
	
	
}
