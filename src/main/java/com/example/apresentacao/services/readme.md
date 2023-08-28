# Aplicação Cliente, Login com Certificado d utilizando Oauth 2 com azure AD #

## 1 Premissas e considerações

 * Para este tutorial utilizamos o Azure APIM para realizar a validaçao do JWT TOken, mais informações localizadas no repo > https://github.com/victorf16/PUBLIC-SpringBootAzureAuthenticationCertificate

 * JAVA 11 ou superior conforme especificação no arquivo POM.xml do projeto

 * Para o projeto, foi considerado uma aplicação cliente que irá se comunicar co umam aplicação backend conforme repo > https://github.com/victorf16/PUBLIC-SpringBootAzureAuthenticationCertificate, que funcionará como uma calculadora que realiza uma SOMA
  ao receber a requisição post no endereco https://*****azurewebsites.com/calculate/sum.

 * Consideramos o Azure APIM conforme doc https://github.com/victorf16/PUBLIC-SpringBootAzureAuthenticationCertificate para validação do token JWT e o azure AD para gerenciamento dos usuários de serviço (service principal) 
  
# Arquitetura final

![image](https://github.com/victorf16/PUBLIC-SpringBootAzureAuthenticationCertificate-/assets/28166733/f4d87fab-ab47-40b3-a6e3-680b58e3ed1c)
