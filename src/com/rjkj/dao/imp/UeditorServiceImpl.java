package com.rjkj.dao.imp;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.MultiState;
import com.baidu.ueditor.define.State;
import com.rjkj.dao.NewsAttachDao;
import com.rjkj.dao.NewsDao;
import com.rjkj.editor.MultipartFile;
import com.rjkj.editor.PathFormat;
import com.rjkj.editor.ueditor.StorageManager;
import com.rjkj.editor.ueditor.UeditorService;
import com.rjkj.entities.News;
import com.rjkj.entities.NewsAttach;
import com.rjkj.util.StringUtil;
import com.rjkj.util.web.Page;


@Transactional
@Service("UeditorServiceImpl")
public class UeditorServiceImpl   implements UeditorService{

	@Autowired
	private NewsAttachDao attService;
	
	@Autowired
	private NewsDao newsService;
	
	String id="";
	
	@Override
	public MultipartFile getMultipartFile(String filedName, HttpServletRequest request,String savePath) {
			return null;
	}

	@Override
	public State saveFileByInputStream(MultipartFile multipartFile, long maxSize,String physicalPath,String savePath,String urlPrefix) {
		
		try {
			if (multipartFile.getSize() > maxSize) {
				return new BaseState(false, AppInfo.MAX_SIZE);
			}
			
			//Map<String, Object> uploadResult = null;
			String name = multipartFile.getOriginalFilename();
			
			//String[] nameArry = name.split("\\.");
			String sss = StringUtils.substringAfterLast(name, ".");
			String suffix = sss.toLowerCase();
			String savename =  new Date().getTime() + "." + suffix;
			
			InputStream inputStream =multipartFile.getInputStream();
			
			State state = StorageManager.saveFileByInputStream(inputStream, physicalPath,maxSize);
			inputStream.close();

			if (state.isSuccess()) {
				//state = new BaseState(true);
				//state.putInfo("size", multipartFile.getSize());
				//state.putInfo("title",name);
				state.putInfo("url", savePath);
				
				// 把上传的文件信息记入数据库
				if(!StringUtil.isEmpty(id)){
					News news=new News();
					news.setId(Long.valueOf(id));
					NewsAttach att=new NewsAttach();
					String pk = UUID.randomUUID().toString();
					pk=pk.replaceAll("-", "");
					att.setId(pk);
					att.setNews(news);
					att.setTitle(name);
					att.setFileOrigName(name);
					att.setSuffix(suffix);
					att.setFileSize(multipartFile.getSize());
					att.setFilePath(savePath);
					this.attService.save(att);
					
					state.putInfo("url", pk);
				}
				//saveFile.delete();
				return state;
				
				
			}

			
			
		} catch (IOException e) {

		}
		return new BaseState(false, AppInfo.IO_ERROR);
	}
	
	
	@Override
	public State saveFileByInputStream(InputStream multipartFile, long maxSize,String savePath) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public State saveBinaryFile(byte[] data, String fileName,String physicalPath,String savePath,String urlPrefix) {
		State state = null;
		String sss = StringUtils.substringAfterLast(fileName, ".");
		String suffix = sss.toLowerCase();
		String savename =  new Date().getTime() + "." + suffix;
		
		
		state = StorageManager.saveBinaryFile(data, physicalPath);

		
		if (state.isSuccess()) {
			//state = new BaseState(true);
			savePath=PathFormat.format(savePath);
			state.putInfo("url", savePath);
			
			
			JSONObject jsonObj = new JSONObject(state.toJSONString());
			Long size=Long.valueOf((String)jsonObj.get("size"));
			
			// 把上传的文件信息记入数据库
			if(!StringUtil.isEmpty(id)){
				News news=new News();
				news.setId(Long.valueOf(id));
				NewsAttach att=new NewsAttach();
				String pk = UUID.randomUUID().toString();
				pk=pk.replaceAll("-", "");
				att.setId(pk);
				att.setNews(news);
				att.setTitle(fileName);
				att.setSuffix(suffix);
				att.setFileSize(size);
				att.setFilePath(savePath);
				this.attService.save(att);
				state.putInfo("url", pk);
			}
			//saveFile.delete();
			return state;
			
			
		
			}else{
				return new BaseState(false, AppInfo.IO_ERROR);
			}
		
	}

	@Override
	public State listFile(String[] allowFiles, int start, int pageSize,String urlPrefix) {
		// 把计入数据库中的文件信息读取出来，返回即可
		State state = new MultiState( true );
		state.putInfo( "start", start);
		state.putInfo( "total", 0);
		if(!StringUtil.isEmpty(id)){
			
			String types="";
			for(int i=0;i<allowFiles.length;i++){
				types=types+",'"+allowFiles[i]+"'";
			}
			types=types.substring(1);
			
			String query=" WHERE o.news.id="+id+" and o.suffix IN("+types+")";
			
			int pageNumber = start / pageSize + 1;
			Page page=new Page();
			page.setPageSize(pageSize);
			page.setPageNo(pageNumber);
			
			List<NewsAttach> list=this.attService.findAll(query, page);
			
			state = this.getState(list,urlPrefix);
			state.putInfo("start", start);
			state.putInfo("total", page.getTotalRows());
			
		}
		return state;
				
	}
	
	
	private State getState(List<NewsAttach> files,String urlPrefix) {
		MultiState state = new MultiState(true);
		BaseState fileState = null;
		//String fileserverurl =  propertiesService.getProperty("FILE_SERVER_URL");
		for (NewsAttach atta : files) {
			fileState = new BaseState(true);
			//String fullurl=basePath+atta.getFilePath();
			//fullurl=PathFormat.format(fullurl);
			fileState.putInfo("url",atta.getId());
			state.addState(fileState);
		}
		return state;
	}

	@Override
	public void setId(String id) {
		this.id=id;
		
	}

}
