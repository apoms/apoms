# tv.admin
 
Image: aetherit/ats.ws

Tags:
- 0.0.1

Prepare Library
1. Download Server JRE to files directory.
    https://github.com/aetheritio/baskit.base -> 0.0.1 release
   
    

Usage:
`docker run -d --name upay.ps
  -p 8090:8090
  -p 8888:8888
  -e TZ=Asia/Seoul
  -e DB_URL=jdbc:mariadb://{개발머신의 아이피(127.0.0.1은 안됨)}:3306/ats?serverTimezone=Asia/Seoul
  -e DB_USERNAME=aetherit
  -e DB_PASSWORD=dnflRlfl
  aetherit/ats.ws:development`
  
DB Usage:
`docker run -d --name upay.db
 -p 3306:3306
 -e TZ=Asia/Seoul
 -e MYSQL_ROOT_PASSWORD=dnflRlfl1535
 -e MYSQL_DATABASE=upay
 -e MYSQL_USER=aetherit
 -e MYSQL_PASSWORD=dnflRlfl
 mariadb:10.4.8
 --character-set-server=utf8mb4
 --collation-server=utf8mb4_unicode_ci`
 