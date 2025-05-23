# Proyecto Demo: mTLS con Spring Boot y NGINX

Este repositorio contiene una demo funcional de comunicación mutua con TLS (mTLS) entre dos servicios Spring Boot usando certificados generados localmente.

## Estructura

- `/backend-service`: API segura que exige mTLS.
- `/client-java`: Cliente Java que consume la API usando `WebClient` con certificados.
- nginx-reverse-proxy/ (proxy mTLS)
