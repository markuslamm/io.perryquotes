## io.perryquotes WIP

![Perry Rhodan](perry.jpg "Perry Rhodan")

I’m a huge Perry Rhodan fan and audiobooker of the Silver Editions from Kassel, Germany. 
This is a little Spring-Boot project to collect cool quotes, to provide a public api for them and to tweet from within the application

Deployed on Heroku.

Workflows:
* Telegram-Component
    - Send a quote to the bot in a specified format
    - Message will be persisted and set to UNPROCESSED state
    - An event will be published and triggers the parsing of the incoming message
  
* Quote-Component
    - Bot message will be parsed, a quote created and stored in the database in REVIEW state
     - An event will be published and triggers the response via existing telegram channel
    - After checking the quote for typos etc, the generated quote changes to COMMITTED state and will be available via public api


* Twitter-Component
  - Committed quotes can be tweeted to the account @perryrhodanquo1
  
TODOS:
- multiple authors, "dialogues"
- "Tag"-domain, search by Tag
- write more tests, parameterized tests
- add security and separate PUBLIC and ADMIN components
- separate integration test
- use WebClient instead of RestTemplate
- improve logging
- get rid of //TODO s
- pagination
- Webapp implementation


