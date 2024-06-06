# IOT 



Instukcja do ogarnięcia HTTPS na szybko w Windows: 

pobierz nginx jeżeli nie masz

openssl req -new -newkey rsa:2048 -nodes -keyout C:\\nginx\\certs\\localhost.key -out C:\\nginx\\certs\\localhost.csr -subj "//C=PL\ST=Wroclaw\L=Wroclaw\O=StudenciPWR\OU=StudenciPWR\CN=localhost"

Utwórz plik C:\nginx\certs\localhost.ext z następującą zawartością:

authorityKeyIdentifier=keyid,issuer
basicConstraints=CA:FALSE
keyUsage = digitalSignature, nonRepudiation, keyEncipherment, dataEncipherment
subjectAltName = @alt_names

[alt_names]
DNS.1 = localhost

openssl x509 -req -in C:\\nginx\\certs\\localhost.csr -signkey C:\\nginx\\certs\\localhost.key -out C:\\nginx\\certs\\localhost.crt -days 365 -extfile C:\\nginx\\certs\\localhost.ext



Skonfiguruj nginx.conf do obsługi Angular przez HTTPS:
worker_processes  1;

events {
    worker_connections  1024;
}

http {
    include       mime.types;
    default_type  application/octet-stream;
    sendfile        on;
    keepalive_timeout  65;

    server {
        listen       80;
        server_name  localhost;

        location / {
            return 301 https://$host$request_uri;
        }
    }

    server {
        listen       443 ssl;
        server_name  localhost;

        ssl_certificate      C:/nginx/certs/localhost.crt;
        ssl_certificate_key  C:/nginx/certs/localhost.key;

        ssl_session_cache    shared:SSL:1m;
        ssl_session_timeout  5m;

        ssl_ciphers  HIGH:!aNULL:!MD5;
        ssl_prefer_server_ciphers  on;

        location / {
            proxy_pass https://localhost:4200;
            proxy_set_header Host $host;
            proxy_set_header X-Real-IP $remote_addr;
            proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
            proxy_set_header X-Forwarded-Proto $scheme;
        }

        location /api {
            proxy_pass https://localhost:8443;
            proxy_set_header Host $host;
            proxy_set_header X-Real-IP $remote_addr;
            proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
            proxy_set_header X-Forwarded-Proto $scheme;
        }

        location /unauthorized {
            proxy_pass https://localhost:8443;
            proxy_set_header Host $host;
            proxy_set_header X-Real-IP $remote_addr;
            proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
            proxy_set_header X-Forwarded-Proto $scheme;
        }
    }
}

Otwórz certyfikat localhost.crt.
Kliknij "Zainstaluj certyfikat".
Wybierz "Lokalna maszyna" i "Zaufane główne urzędy certyfikacji".

Uruchom Angular CLI z konfiguracją HTTPS:

ng serve --ssl --ssl-cert "C:/nginx/certs/localhost.crt" --ssl-key "C:/nginx/certs/localhost.key"
