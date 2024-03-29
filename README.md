# Tech Challange Microsserviço - Pipeline para processamento de vídeos

# Arquitetura
![pipeline para processamento de videos](https://firebasestorage.googleapis.com/v0/b/app-english-class.appspot.com/o/postech-fase4%2Farquitetura-processamento-de-videos.png?alt=media&token=442dd15b-e4be-4478-84fa-6f9bae08633a)


## Estudo de caso

### Problema
Quando estamos dentro do transporte coletivo, em algum restaurante com wifi disponível, ou até mesmo em algum shopping center e queremos terminar o capítulo de uma serie, ou assistir um determinado vídeo que nos enviaram, se você não estiver com uma boa conexão disponível em seu celular, ou o wifi disponível dentro estabalecimento estiver com muitas pessoas conectadas, será muito difícil você conseguir consumir alguma midia com uma boa qualidade de imagem, porque ela poderá demorar para carregar ou muito provavelmente ficará travando com o decorrer do vídeo.
Por isso alguns dos principais players de stream do mercado, utilizam algumas tecnologias específicas para diminuir esses travamentos, e por consequência economizar o plano dos seus dados móveis. Algumas dessas tecnologias, descrevo com mais no tópico abaíxo.

### Protocolos de reprodução de vídeo

- **Apple HLS**: O protocolo HLS, lançado em 2009 pela Apple, em princípio para funcionarem apenas em seus Iphones porém logo se espalhou pelo mercado e atualmente é o protocolo mais utilizado para reprodução de vídeos. A principal função do HLS é dividir os arquivos de vídeo em arquivos menores e distribuí-los utilizando o protocolo HTTP. Os players de stream carregam esses arquivos em forma de vídeo para que o consumidor possa assistir. Podemos escolher o tamanho de cada arquivo, aqui na aplicação eu utilizei um tamanho fixo de 10 segundos nas resoluções 480p, 720p e 1080p.
- **IIS Smooth Streaming**: O protocolo de transporte smooth streaming desenvolvido pela Microsoft, também é uma tecnologia de streaming adaptável, também utiliza o mesmo conceito do Apple HLS, ou seja utiliza-se o HTTP para transmitir suas mídias, ao player de destino. Porém esse protocolo é compatível apenas com os players de mídia baseados no Silverlight, mas observando o nosso cenário de stream que estamos construindo, não seria algo muito interessante para se adotar.   
- **Dash ISO** É um padrão desenvolvido pela International Organization for Standardization (ISO), não é muito diferente dos outros protocolos citados anteriormente, porém com uma diferença em relação ao Smooth Streaming, ele funciona nos players mais modernos do mercado, facilitando assim sua adoção.

### Porque adotamos o HLS para desenvolvimento dessa pipeline? 

Antes de iniciar qualquer desenvolvimento, tive que responder todas essas perguntas: 
1. Qual a arquitetura os principais players de streaming do mercado utilizam?
     - Referências:
         - [arquitetura de streaming de vídeos](https://aws.amazon.com/pt/developer/application-security-performance/articles/video-streaming-architectures/)
         - [cloudfront streaming](https://aws.amazon.com/pt/cloudfront/streaming/)
         - [cloud front para streaming de midias - documento PDF](https://d1.awsstatic.com/whitepapers/amazon-cloudfront-for-media.pdf)
         - [o que é adaptative bitrate streaming](https://www.cloudflare.com/learning/video/what-is-adaptive-bitrate-streaming/)
2. Como funciona o streaming de vídeo sob-demanda e ao-vivo.
    - referências:
        - [o que é live streaming](https://www.cloudflare.com/pt-br/learning/video/what-is-live-streaming/)
      
3. Qual é o melhor cloud provider para se utilizar quando estamos trabalhando com processamento de vídeos. Nessa pergunta considerei os seguintes critérios: serviços de encoder e entrega de mídia, SDKs para integração entre o serviço e a api, documentações e o preço de cada um deles.
    - referências:
        - [arquitetura de streaming de vídeos](https://aws.amazon.com/pt/developer/application-security-performance/articles/video-streaming-architectures/)
        - AWS Skill Builder - Setting Up and Configuring AWS Elemental MediaConvert
        - AWS Skill Builder - AWS Elemental MediaConvert Primer
        - AWS Skill Builder - Creating Adaptive Bitrate (ABR) Outputs
        - [SDK AWS Java 2.x AWS Elemental Media Converter](https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/java_mediaconvert_code_examples.html)
4. Considerando o nosso cenário, qual é o melhor protocolo para se utilizar.
    - referências:
        - [HLS vs Dash](https://www.cloudflare.com/pt-br/learning/video/what-is-mpeg-dash/)
        - [protocólos de streaming de vídeos](https://www.dacast.com/blog/video-streaming-protocol/)

Após responder a todas essas perguntas, optei por utilizar a AWS, porque temos disponível uma grande prateleira de serviços que se iniciam com o processamento das mídias até a entrega ao usuário final consumir. É possível também integrar cada serviço utilizando os SDKs e o bom preço praticado na utilização de cada um, e por fim uma boa documentação e contém também uma plataforma para treinamento, no qual podemos utilizar para entendimento prático de cada serviços que vamos utilizar. 

### Treinamento e desenvolvimento

Com todas essas informações em mãos, antes de iniciar o desenvolvimento da funcionalidade, precisamos criar uma lista de cada serviço que será utilizado. Como vimos anteriormente na arquitetura geral do projeto, estou chamando esse microsserviço de pipeline de processamento de vídeo, porque a mídia do usuário passará por várias peças até ao destino final, que são os vídeos processados em pequenas partes e adaptáveis a diversas resoluções. 
Os serviços que estou utilizando foram os seguintes: AWS SQS, Media Convert, AWS S3 e API (java versão 17).
Após criarmos e avaliarmos a arquitetura vamos ao próximo passo que é selecionar os serviços no qual não conheço e fazer algum treinamento para entender em detalhes a sua utilização. No meu cenário eu não conhecia o Media Convert, portanto acessei o Skill Builder da AWS e realizei 3 treinamentos; **Setting Up and Configuring AWS Elemental MediaConvert**, **AWS Elemental MediaConvert Primer** e **Creating Adaptive Bitrate (ABR) Outputs**. Após terminar cada curso, me senti confortável em iniciar o desenvolvimento da funcionalidade. 

Para iniciar o desenvolvimento, precisei seguir o que a documentação disponibilizada pela AWS [AWS SDK Java 2.x Media Convert](https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/java_mediaconvert_code_examples.html) para iniciar a integração com o serviço. Como o código que está disponibilizado na documentação é muito extenso, optei por integrar exatamente igual como está descrito na documentação, o intuito em principio era fazer a integração funcionar, sem se preocupar com arquiteturas. Após tudo integrado, aos poucos comecei a adaptar ao meu cenário, separando o código em pequenas partes para facilitar os testes e a leitura do mesmo, alterando os parâmetros para ficarem conformidade com o que eu já tinha criado no console da AWS e sabia que se encaixava perfeitamente no meu cenário. 

Durante o processo de desenvolvimento, tive que entender o objetivo geral de alguns conceitos antes de finalizar a integração da API, são eles; bitrate, segments, resoluções e tipos de codecs. 

- **Segments**: Seu objetivo é definir o tamanho mínimo e máximo que desejo separar essa mídia. Optei por separar em pacotes de no máximo 10 segundos, assim a internet dos usuário que estão utilizando dados móveis não será muito consumida, e enviamos pela rede pequenos pacotes logo eles são carregados de maneira mais rápida em nossos aparelhos.

- **Bitrate**: A configuração do bitrate em nosso cenário é essencial para uma boa experiência, pois quanto mais baixo, pior será a resolução, porém quanto mais alto melhor a resolução e mais pesado será o arquivo gerado, então temos que calcular um bitrate ideal para cada tipo de resolução. Para definir o bitrate ideal, optei por utilizar exatamente os mesmo que são utilizados no youtube. [tabela de bitrate](https://firebasestorage.googleapis.com/v0/b/app-english-class.appspot.com/o/postech-fase4%2Fbitrate.png?alt=media&token=7344ce95-f8d6-46fb-aac5-8db2bfeef03c). Porém no código tenho que adicionar um valor inteiro, para isso tive que fazer um simples cálculo, convertendo de MB para Bits. Por exemplo, no código optei por utilizar a resolução 480P com 4000000 de bits ou 4 milhões de bitrate, isso equivale a 4 MB na documentação apresentada no link acima (4 MB * 1000000 Bits = 4000000)

- **Resoluções**: Definir 3 diferentes tipos de resoluções para processamento dos vídeos, 480p, 720p e 1080. Atualmente são as resoluções que a maioria dos usuários conseguem reproduzir em seus aparelhos, e definindo uma boa taxa de bitrate conseguimos uma boa qualidade de imagem para cada tipo de resolução.
  
- **Codecs**: Para integração estão disponíveis 2 tipos de codecs, H264 e H265. Optei por seguir utilizando o H264, já que este se encaixa perfeitamente em nosso cenário. Também poderíamos utilizar o codec H265 se quiséssemos uma melhor eficiência na compreensão nos arquivos de vídeos, reduzir o consumo da largura de banda dos nossos usuários. Porém como eu já tinha selecionado arquivos com tamanho máximo de 10 segundos, consegui reduzir bastante a largura de banda consumida, então ao fazer os testes não percebi uma notável diferença em relação ao H264 e o H265. 
  


### Segue outras referências que precisei consultar durante o processo de desenvolvimento

[taxa de bitrate recomendadas pelo youtube](https://support.google.com/youtube/answer/1722171?hl=en&ref_topic=9257782&sjid=16957253743887332310-SA)

[resoluções de vídeo recomendadas pelo youtube](https://support.google.com/youtube/answer/6375112?hl=en&ref_topic=9257782&sjid=16957253743887332310-SA)

[o que é bitrate](https://canaltech.com.br/internet/o-que-e-bitrate-e-como-isso-influencia-na-qualidade-dos-videos-162423/)

[codec H265](https://tecnoblog.net/responde/o-que-e-h-265/)

[codec h264 vs h265](https://aws.amazon.com/pt/media/tech/high-efficiency-video-coding/)

### Segue as classes de configuração de bitreate, codecs e segmentação
[configuração do codec 264, taxa de bits e resolução](https://github.com/JonasBarros1998/processamento-de-videos/blob/main/src/main/java/com/br/stream/processamentoDeVideo/infra/processarMidias/gruposDeSaida/AppleHLSH264.java)

[configuração do segmento](https://github.com/JonasBarros1998/processamento-de-videos/blob/main/src/main/java/com/br/stream/processamentoDeVideo/infra/processarMidias/gruposDeSaida/AppleHLSContainer.java) 


