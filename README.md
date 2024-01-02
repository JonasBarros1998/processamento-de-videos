# Tech Challange Microsserviço - Pipeline para processamento de vídeos

# Arquitetura
![pipeline para processamento de videos](https://firebasestorage.googleapis.com/v0/b/app-english-class.appspot.com/o/postech-fase4%2FPipelineParaProcessamentoDeVideos.drawio.png?alt=media&token=ca7b9978-1382-46d4-8822-9d6b7b63000f)


## Estudo de caso

### Problema
Quando estamos dentro do transporte coletivo, em algum restaurante com wifi disponível, ou até mesmo em algum shopping center, e queremos terminar o capítulo de uma serie, ou assistir um determinado vídeo que nos enviaram, porém se você não estiver com uma boa conexão disponivel em seu celular, ou o wifi disponível dentro estabalecimento estiver com muitas pessoas conectadas, será muito difícil você conseguir consumir alguma midia com uma boa qualidade porque ela poderá demorar para carregar ou muito provavelmente ficará travando ao decorrer do vídeo.
Por isso alguns dos principais players de stream do mercado, utilizam algumas tecnologias específicas para diminuir esses travamentos, e por consequencia economizar o plano dos seus dados móveis. Algumas dessas tecnologias, descrevo com mais no tópico abaíxo.

### Protocolos de reprodução de vídeo

- **Apple HLS**: O portocolo HLS, lançado em 2009 pela Apple, em principio para funcionarem apenas em seus Iphones porém logo se espalhou pelo mercado e atualmente é o protocolo mais utilizado para reprodução de vídeos. A principal função do HLS é dividir os arquivos de vídeo em arquivos menores e distribuí-los utilizando o protocolo HTTP. Os players de stream carregam esses arquivos em forma de vídeo para que o consumidor poder assistir. Podemos escolher o tamanho da cada arquivo, aqui na aplicação eu utilizei um tamanho fixo de 10 segundos e em multiplas resoluções.
- **IIS Smooth Streaming**: O protocolo de transporte smooth streaming desenvolvido pela Microsoft, também é uma tecnologia de streaming adaptável, também utiliza o mesmo conceito do Apple HLS, utiliza HTTP para transmitir suas mídias, ao player de destino. Porém esse protocolo é compaível apenas com os players de midia baseados no Silverlight que observando o nosso cenário de stream que estamos contruindo, não seria algo muito interessante para se adotar.   
- **Dash ISO** É um padrão desenvolvido pela International Organization for Standardization (ISO), não é muito diferentes dos outros protocolos citados anteriormente, porém com uma diferença em relação ao Smooth Streaming, ele funciona nos players mais modernos do mercado, facilitando assim sua adoção.

### Porque adotamos o HLS para desenvolvimento dessa pipeline? 

Antes de iniciar qualquer desenvolvimento, tive que responder todas essas perguntas: 
1. Qual a arquitetura os principais players de streaming do mercado utilizam?
3. Como funciona o streaming de vídeo sob-demanda e ao-vivo.
    - referências: https://aws.amazon.com/pt/developer/application-security-performance/articles/video-streaming-architectures/
    - https://www.cloudflare.com/pt-br/learning/video/what-is-live-streaming/
      
5. Qual é o melhor cloud provider para se utilizar quando estamos trabalhando com processamento de vídeos. Nessa pergunta considerei os seguintes itens: serviços, SDKs, preço, 
    - referências: https://aws.amazon.com/pt/developer/application-security-performance/articles/video-streaming-architectures/
7. Considerando o nosso cenário, qual é o melhor protocolo para se utilizar.
   - referências: https://www.cloudflare.com/pt-br/learning/video/what-is-http-live-streaming/
5- 


