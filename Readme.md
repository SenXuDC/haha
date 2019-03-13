### fastdfs
##### 启动tracker
docker run -dti -p 22122:22122 --name tracker -v ~/fdfs/tracker:/var/fdfs ygqygq2/fastdfs-nginx tracker
docker run -dti --net=host --name tracker -v ~/fdfs/tracker:/var/fdfs ygqygq2/fastdfs-nginx tracker
##### 启动storage0
docker run -dti -p 8080:8080 -p 23000:23000 -p 8888:8888 -p 80:80 --name storage0 -e TRACKER_SERVER=192.168.1.101:22122 -v ~/fdfs/storage0:/var/fdfs ygqygq2/fastdfs-nginx storage
docker run -dti --net=host --name storage0 -e TRACKER_SERVER=192.168.1.101:22122 -v ~/fdfs/storage0:/var/fdfs ygqygq2/fastdfs-nginx storage
