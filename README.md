![](https://upload-images.jianshu.io/upload_images/14189142-e8e9fd762d4aa4cc.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

>system

> data

> storage

## system

通过Environment.getRootDirectory()获取，内部存储了framework等系统需要的文件，该文件只拥有可读权限，获取并显示其中的文件列表

该文件占用的大小约为2G
![/system](https://upload-images.jianshu.io/upload_images/14189142-5ebcafe1746d96dd.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## data
Android把内存分为Internal Storage 和 External Storage，即为内部存储和外部存储（SD卡存储）

Internal Storage中的文件通过
Environment.getDataDirectory()获取 /data 目录的文件
Context.getFilesDir().getParentFile()获取 /data/user/0/包名 目录的文件

应用内分配的存储空间 /data/user/0/包名 位于 /data 中，解析 /data/user/0/包名 的文件空间如下：
![](https://upload-images.jianshu.io/upload_images/14189142-2bdc4342bcdf1acb.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
读取到的总空间大小为16M，但是可用空间读取到接近6G，猜测分配给应用的私有空间会动态分配，并且这个读取到的可用空间是变化的 /data/user/0/包名 中的文件结构如下所示：
![](https://upload-images.jianshu.io/upload_images/14189142-2ed0b4d6643071e4.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
files文件通过Context.getFilesDir()获取，其作用是存放应用私有数据，只有本应用有读写的权限，随应用的删除而删除
 
cache文件通过Context.getCacheDir()获取，其作用是存储缓存数据，可被手机管家等将其中的数据删除

应用默认自带读写权限

开发中应用对 /data/user/0/包名 有读写权限，但是对 /data/user/0 没有读写权限，同时，对于 /data 我也无法获取其读写权限！

## storage
通过Environment.getExternalStorageDirectory()获取根目录 /storage/emulated/0 ，用于存储应用的私有、公开数据，系统级的公开数据等，该目录就是手机文件管理器打开的文件根目录，其结构可能如下：
![](https://upload-images.jianshu.io/upload_images/14189142-cbc6509514fd4077.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
其中Download、DCIN、Pictures等就是系统级的公开信息存储位置，合理的使用这些文件，在使用Pictures的时候应该在其中创建应用命名的文件夹，便于管理自己应用产生的文件

其中Android文件夹中的data中的包名下就是应用的外部文件应用私有文件区域，其下基本结构如下：
![](https://upload-images.jianshu.io/upload_images/14189142-00ffd8d46985d898.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

应用可以在files中创建存储文件，文件为应用私有，应用卸载时会随之删除，可通过FileProvider等对外提供文件的短暂访问

对于 /storage/emulated/0 内存空间，如下：
![](https://upload-images.jianshu.io/upload_images/14189142-c26d3752b5d4a349.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

我的手机是64G的内存，但是读取到的总内存只有53G也就是用户可用的部分

Note：运行结果受手机影响！
