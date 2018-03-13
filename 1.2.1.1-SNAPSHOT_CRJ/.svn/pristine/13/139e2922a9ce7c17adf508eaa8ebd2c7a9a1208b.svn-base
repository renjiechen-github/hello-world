package pccom.common.util;

import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import org.slf4j.Logger;

import pccom.common.SQLconfig;

public class WatchFile {
	public static final Logger logger = org.slf4j.LoggerFactory.getLogger(Validator.class);
	public void watch(String pathname){
		WatchService watchService;
		try {
			watchService = FileSystems.getDefault().newWatchService();
		 logger.debug("开始监控文件"+pathname);
//        Paths.get("d:/").register(watchService,   
//                StandardWatchEventKinds.ENTRY_CREATE,  
//                StandardWatchEventKinds.ENTRY_DELETE,  
//                StandardWatchEventKinds.ENTRY_MODIFY);  
			Paths.get(pathname).register(watchService,   
	                StandardWatchEventKinds.ENTRY_CREATE,  
	                StandardWatchEventKinds.ENTRY_DELETE,  
	                StandardWatchEventKinds.ENTRY_MODIFY);  
	        while(true)  
	        {  
	            WatchKey key=watchService.take();  
	            for(WatchEvent<?> event:key.pollEvents())  
	            {  
	                logger.debug(event.context()+"发生了"+event.kind()+"事件");  
	                try{
	                	if("sqldata.json".equals(event.context().toString())){
		            		SQLconfig.init();
		            	}else if("configuration.properties".equals(event.context().toString())){
		            		Constants.set();
		            	}
	                }catch(Exception e){
	                	logger.debug("监控失败，出现错误"+e.getMessage());
	                }
	            }  
	            if(!key.reset())  
	            {  
	                break;  
	            }  
	        }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
}
