## io.perryquotes WIP

![Perry Rhodan](perry.jpg "Perry Rhodan")

I’m a huge Perry Rhodan fan and audiobooker of the Silver Editions from Kassel, Germany. 
This is a little Spring-Boot project to collect quotes, to provide a public api and to make cool Perry Rhodan tweets..;)

Workflows:
* Telegram-Component
    - Send a quote to the bot in a specified format
    - Message will be persisted and set to UNPROCESSED state
    - An event will be published and triggers the parsing of the incoming message
  
    
* Quote-Component
    - Bot message will be parsed, a quote created and stored in the database in REVIEW state
     - An event will be published and triggers the response via telegram existing channel
    - After checking the quote for typos etc, the generated quote changes to state COMMITTED and will be available via public api


* Twitter-Component
  - Tweet committed quotes to the account @perryrhodanquo1


