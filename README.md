[![Build Status](https://travis-ci.org/mindmill/ladon-s3-cassandra.svg?branch=master)](https://travis-ci.org/mindmill/ladon-s3-cassandra)
# Ladon S3 Cassandra  #
*this project is no longer under active development, have a look at its successor: https://github.com/mindmill/ladon-data-center-edition*
 
 ### Installation:
 The Ladon S3 Cassandra is built using Java 8 , Kotlin and Maven
 ```bash
 mvn package && java -jar target/ladon_se_1.4.1.jar 
 ```
please make sure you did install Ladon S3 Server first, since this project depends on it
```bash
sh ./install-dependencies.sh
```
[https://github.com/mindmill/ladon-s3-server](https://github.com/mindmill/ladon-s3-server)
### Login

Navigate your browser to  [http://localhost:8080/admin/overview](http://localhost:8080/admin/overview)
 
 Username: admin, password: admin123

### License
Copyright (C) 2017 Mind Consulting

Free for private use, easy commercial licensing available

<a href="http://mind-consulting.de/"><img src="http://mind-consulting.de/img/logo_no_bg.png"  height="100" width="250" ></a>

