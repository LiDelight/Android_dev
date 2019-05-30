package edu.cqut.MobileOrderFood;

import android.content.Context;
import android.content.res.Resources;
import android.os.Environment;
import android.os.StatFs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class DataFileAccess 
{
	private Context mContext;
	private String mSDPath; //SD卡路径 
	
	DataFileAccess(Context cont)
	{
		mContext = cont;
		mSDPath = Environment.getExternalStorageDirectory().getPath() + "/"; 
	}
	
    /**判断SDCard是否存在？是否可以进行读写*/
    public boolean SDCardState()
    {
	    if(Environment.getExternalStorageState().equals (
	            Environment.MEDIA_MOUNTED)) //表示SDCard存在并且可以读写
	    {	
	        return true;
	    }
	    else
	    {
	        return false;
	    }
    }
    
    /**获取SDCard文件路径*/
    public String SDCardPath()
    {	
	    if(SDCardState()){//如果SDCard存在并且可以读写	
	    	mSDPath = Environment.getExternalStorageDirectory().getPath();
	        return mSDPath;	
	    }
	    else{
	        return null;
	    }
    }

    /**获取SDCard 可用容量大小(MB)*/
    public long SDCardFree()
    {
	    if(null!=SDCardPath()){
	        StatFs statfs = new StatFs(SDCardPath());
	        //获取SDCard的Block可用数
	        long availaBlocks = statfs.getAvailableBlocks();
	        //获取每个block的大小
	        long blockSize = statfs.getBlockSize();
	        //计算SDCard 可用容量大小MB	
	        long SDFreeSize = availaBlocks*blockSize/1024/1024;
	        return SDFreeSize;
	
	    }
	    else{	
	        return 0;	
	    }
    }
	
    /** 
     * 在SD卡上创建目录 
     * @param dirName  要创建的目录名 
     * @return 创建得到的目录 
     */  
    public boolean createSDDir(String dirName) 
    {
    	String [] strSubDir = dirName.split("/");
    	String strCurrentPath = mSDPath;
    	for (int i=0; i<strSubDir.length; i++) {
    		strCurrentPath += "/" + strSubDir[i];
    		File curDir = new File(strCurrentPath);
    		if (!curDir.exists()) { //当前目录不存在
    			//创建目录
    			boolean isCreated = curDir.mkdir();
    			if (!isCreated) { //目录创建失败
    				System.out.println(strCurrentPath + " 创建失败！");
    				return false;
    			}
    		}
    	}
    	return true;
    }  

    /** 
     * 删除SD卡上的目录 
     * @param dirName 
     */  
    public boolean delSDDir(String dirName)
    {  
        File dir = new File(mSDPath + "/" + dirName);  
        return delDir(dir);  
    }  

    /** 
     * 删除一个目录（可以是非空目录） 
     * @param dir 
     */  
    public boolean delDir(File dir) 
    {  
        if (dir == null || !dir.exists() || dir.isFile()) {  
            return false;  
        }  

        for (File file : dir.listFiles())
        {  
            if (file.isFile()) {  
                file.delete();  
            } else if (file.isDirectory()) {  
                delDir(file);// 递归  
            }  
        }  
        dir.delete();  
        return true;  
    }      
    
    /** 
     * 在SD卡上创建文件 
     * @throws IOException 
     */  
    public File createSDFile(String fileName) throws IOException 
    {  
        File file = new File(mSDPath +"/"+ fileName);  
        System.out.println(mSDPath+"/" + fileName);
        file.createNewFile();  
        return file;  
    }  

    /** 
     * 判断文件是否已经存在 
     * @param fileName,  要检查的文件名 
     * @return boolean, true表示存在，false表示不存在 
     */  
    public boolean isFileExist(String fileName) 
    {  
        File file = new File(mSDPath + "/" + fileName);  
        boolean isExisted =  file.exists();  
        return isExisted;
    } 

    /** 
     * 删除SD卡上的文件 
     * @param fileName 
     */  
    public boolean delSDFile(String fileName) 
    {  
        File file = new File(mSDPath + fileName);  
        if (file == null || !file.exists() || file.isDirectory())  
            return false;  
        file.delete();  
        return true;  
    }      
    
    /** 
     * 拷贝一个文件,srcFile源文件，destFile目标文件 
     * @param path 
     * @throws IOException 
     */  
    public boolean copyFileTo(File srcFile, File destFile) throws IOException {  

        if (srcFile.isDirectory() || destFile.isDirectory())  
            return false;// 判断是否是文件  
        FileInputStream fis = new FileInputStream(srcFile);  
        FileOutputStream fos = new FileOutputStream(destFile);  
        int readLen = 0;  
        byte[] buf = new byte[1024];  
        while ((readLen = fis.read(buf)) != -1) {  
            fos.write(buf, 0, readLen);  
        }  
        fos.flush();  
        fos.close();  
        fis.close();  
        return true;  
    }    
    
    //该函数将文件存储到内部存储器的文件夹
    public void SaveFile(String fileName, byte[] fileData)
    {
    	try {
    		FileOutputStream fos = mContext.openFileOutput(fileName, Context.MODE_PRIVATE);
    		fos.write(fileData);//将fileData里的数据写入到输出流中
    		fos.flush();//将输出流中所有数据写入文件
    		fos.close();//关闭输出流
    	}
    	catch (Exception e) {
    		
    	}
    }
    
    /** 
     * 将raw文件夹中的资源文件复制到SD卡中的指定文件夹中
     * @param resFileId: raw文件夹中的文件id号
     * @param strSDFileName:sd卡中的文件路径 ，相对路径
     */
    public boolean CopyRawFilestoSD(int resFileId, String strSDFileName)
    {
    	//获得资源实例
    	Resources resources = mContext.getResources();
    	InputStream inputStream = null; //二进制输入流
    	try {
    		File sdFile = new File(mSDPath + "/" + strSDFileName); 
    		sdFile.createNewFile(); //创建新文件
    		//判断SD文件是否存在、可写，且不是目录
    		if (!(sdFile.exists() && sdFile.canWrite()) || sdFile.isDirectory())
    				return false;
    		//创建文件输出流
    		FileOutputStream fos = new FileOutputStream(sdFile);
    		//打开资源文件，获得二进制输入流
    		inputStream = resources.openRawResource(resFileId);
    		byte [] readerbuf = new byte[1024];//资源缓冲区
    		int readLen = 0; 
    		while ((readLen = inputStream.read(readerbuf)) != -1) {
    			fos.write(readerbuf, 0, readLen);  
    		}
    		fos.flush(); //由缓冲区写入SD卡
    		fos.close();
    		inputStream.close();
    	}catch (Exception e) {
    		return false;
    	}
    	return true;    	
    }
    
    //该函数将用户信息保存到内部存储器的文件
    public void SaveUserInfotoFile(String fileName, MyUser user)
    {
    	try {
    		FileOutputStream fos = mContext.openFileOutput(fileName, Context.MODE_PRIVATE);
    		byte [] idbuf = user.mUserid.getBytes(Charset.forName("UTF-8"));
    		byte bufsize = (byte)idbuf.length;
    		fos.write(bufsize);//写入用户名字节长度
    		fos.write(idbuf);//将mUserid写入到输出流中
    		
    		byte [] psdbuf = user.mPassword.getBytes();
    		bufsize = (byte)psdbuf.length;
    		fos.write(bufsize);//写入用户密码字节长度
    		fos.write(psdbuf);//将mPassword写入到输出流中
    		
    		byte [] phonebuf = user.mUserphone.getBytes();
    		bufsize = (byte)phonebuf.length;
    		fos.write(bufsize);//写入用户电话号码字节长度
    		fos.write(phonebuf);//将mUserphone写入到输出流中
    		
    		byte[] addbuf = user.mUseraddress.getBytes(Charset.forName("UTF-8"));
    		bufsize = (byte)addbuf.length;
    		fos.write(bufsize);//写入用户地址字节长度
    		fos.write(addbuf);//将mUseraddress写入到输出流中
    		
    		fos.flush();//将输出流中所有数据写入文件
    		fos.close();//关闭输出流
    	}
    	catch (Exception e) {
    		
    	}
    }    
    
    //该函数将保存在内部存储器上的用户信息文件读出
    public MyUser ReadUserInfofromFile(String fileName)
    {
    	MyUser userinfo = null;
    	try {
    		FileInputStream fis = mContext.openFileInput(fileName);
    		int fileLen = fis.available();
    		if (fileLen == 0)
    			return null;
    		userinfo = new MyUser();
    		//读入用户名信息
    		byte bufsize = (byte)fis.read();//读入用户名长度
    		byte[] idbuf = new byte[bufsize];
    		fis.read(idbuf);
    		userinfo.mUserid = new String(idbuf, "UTF-8");
    		//读入用户密码
    		bufsize = (byte)fis.read();
    		byte[] psdbuf = new byte[bufsize];
    		fis.read(psdbuf);
    		userinfo.mPassword = new String(psdbuf);
    		//读入用户电话
    		bufsize = (byte)fis.read();
    		byte[] phonebuf = new byte[bufsize];
    		fis.read(phonebuf);
    		userinfo.mUserphone = new String(phonebuf);
    		//读入用户地址
    		bufsize = (byte)fis.read();
    		byte[] addbuf = new byte[bufsize];
    		fis.read(addbuf);
    		userinfo.mUseraddress = new String(addbuf, "UTF-8");
    	}
    	catch (Exception e) {
    		
    	}
    	return userinfo;
    }
}
