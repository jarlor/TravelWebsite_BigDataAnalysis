package Util;

import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;

import java.io.IOException;

/**
 * @author GCJL
 * @date 2021/5/6 13:44
 */
public class HDFSUtil {
    private static FileSystem fs;

    private static void openFS() {
        try {
            fs = Connected.getHDFS();//获取hbase连接
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void closeFS() {
        try {
            fs.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传本地文件到HDFS
     *
     * @param localFile  本地文件路径
     * @param remoteFile 要传到HDFS上的目标路径
     * @throws IOException
     */
    public static void uploadFile(Path localFile, Path remoteFile) throws IOException {
        openFS();
        fs.copyFromLocalFile(localFile, remoteFile);
        closeFS();
    }

    /**
     * 从HDFS下载文件到本地
     * @param localFile
     * @param remoteFile
     * @throws IOException
     */
    public static void downloadFile(Path localFile, Path remoteFile) throws IOException {
        openFS();
        fs.copyToLocalFile(localFile, remoteFile);
        closeFS();
    }

    /**
     * 读取文件内容
     * @param targetPath
     * @throws IOException
     */
    public static void readFile(Path targetPath) throws IOException {
        openFS();
        FSDataInputStream open = fs.open(targetPath);
        IOUtils.copyBytes(open,System.out,1024);
        closeFS();
    }

    /**
     * 文件重命名
     * @param absolutePath 要更改的文件的绝对路径
     * @param newName   要改成什么名字
     * @throws IOException
     */
    public static void renameFile(Path absolutePath,String newName) throws IOException {
        openFS();
        Path targetPath = new Path(absolutePath.getParent().toString() +"/"+ newName);
        fs.rename(absolutePath,targetPath);
        closeFS();
    }
    public static void moveFile(Path oldPath,Path newPath) throws IOException {
        openFS();
        FSDataInputStream fdis = fs.open(oldPath);
        FSDataOutputStream fdos = fs.create(newPath);

        IOUtils.copyBytes(fdis,fdos,1024);
        fs.delete(oldPath,true);
        closeFS();
    }
    /**
     * 遍历指定目录下的文件并打印出来
     *
     * @param targetPath 目标路径
     * @throws IOException
     */
    public static void listFiles(Path targetPath) throws IOException {
        openFS();
        FileStatus[] fss = fs.listStatus(targetPath);

        if (targetPath.getName().equals("")) {
            System.out.println("/");
        } else {
            System.out.println(targetPath.getName());
        }

        for (FileStatus f : fss) {
            //如果是文件夹
            if (f.isDirectory()) {
                for (int i = 0; i < f.getPath().depth(); i++)
                    System.out.print("-");
                listFiles(f.getPath());
            }
            //如果是文件
            if (f.isFile()) {
                for (int i = 0; i < f.getPath().depth(); i++)
                    System.out.print("-");
                System.out.print("F:" + f.getPath().getName() + "\n");
            }

        }
        closeFS();
    }

    /**
     * 创建文件
     *
     * @param targetPath 目标路径
     * @param recursive  是否递归创建
     * @throws IOException
     */
    public static void mkFile(Path targetPath, boolean recursive) throws IOException {
        openFS();
        String src = targetPath.toString();
        String[] split = src.split("/");
        if (!split[0].equals("")) {
            System.out.println("路径错误!");
        } else {
            StringBuilder sb = new StringBuilder();
            Path newsrc = null;
            for (int i = 1; i < split.length; i++) {
                sb.append("/").append(split[i]);
                newsrc = new Path(sb.toString());

                if (recursive) {  //是否允许递归创建
                    //判断这个文件或者目录是否存在
                    if (!fs.exists(newsrc)) {
                        //判断是不是最后一个，如果是，就创建文件，不是就创建文件夹
                        if (i == split.length - 1) {
                            fs.create(newsrc);
                        } else {
                            fs.mkdirs(newsrc);
                        }
                    }
                }
            }
        }
        closeFS();
    }

    /**
     * 创建文件夹
     *
     * @param targetPath 目标路径
     * @throws IOException
     */
    public static void mkDirectory(Path targetPath) throws IOException {
        openFS();
        fs.create(targetPath);
        closeFS();
    }

    /**
     * 删除文件或者文件夹
     * @param targetPath 目标路径
     * @param recursive 是否递归删除
     * @throws IOException
     */
    public static void delete(Path targetPath, boolean recursive) throws IOException {
        openFS();
        //判断是否存在
        if (fs.exists(targetPath)) {
            fs.delete(targetPath, recursive);
        }else {
            System.out.println("目标路径不存在！");
        }
        closeFS();
    }


}
