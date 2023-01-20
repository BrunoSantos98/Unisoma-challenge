# Projeto desafio Unisoma!

> ℹ️ Detalhes: Aplicação Back-end feita em java + Spring boot + postgresql, que tem como finalidade cadastrar funcionarios, calcular novo salario e calcular taxa de imposto a ser pagai.

## 💻 Pré Requisitos 💻:

- Sistema operacional: Qualquer Distribuição Linux, Windows e Mac OS

- Instalação: é necessario ter o java 18(jre e sdk) instalado em sua maquina para testar e qualquer IDE java ou um editor de sua preferencia que consiga compilar java.

## 📃 Como usar:

1. Faça o clone do projeto

2. abra na sua IDE ou editor de preferencia

3. abra o arquivo "pom.xml" e verifique se falta instalar alguma depencia, caso seja necessario faça o Download das dependencias faltantes para o bom funcionamento do projeto

4. na pasta src/main crie um diretorio chamado "resources" e dentro dele um arquivo "application.properties" e adicione as configuraćões relacionadas ao seu banco de dados.
    <br/>Exemplo:
    
   ``` 
        spring.jpa.database=POSTGRESQL
        spring.datasource.platform=postgres
        spring.jpa.show-sql=true
        spring.jpa.hibernate.ddl-auto=create-drop   
        spring.database.driverClassName=org.postgresql.Driver
        spring.datasource.url=jdbc:postgresql://localhost:5432/unisoma
        spring.datasource.username=postgres
        spring.datasource.password=senha
   ```

5. Teste como quiser.
