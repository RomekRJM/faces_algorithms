faces_algorithms
================

Project used to gather nationality data of countries and famous nationals along with pictures for my upcomming android app.
Wikipedia has been used as a source of data. All images are on Creative Commons licence and have been mostly fetched 
from wiki pages with bash/jQuery scripts.

All meaningfull stuff can be found in resources folder:

<b>/list.json</b> - contains encoded countries, like :

```
{  
      "flag":"23px-Flag_of_the_United_Arab_Emirates.png",
      "name":"United Arab Emirates",
      "disabled":false,
      "neighbours":[  
         {  
            "type":"UNSPECIFIED",
            "neighbour":{  
               "name":"Saudi Arabia",
               "disabled":false,
               "neighbours":[  
               ]
            }
         }
}
```

<b>/flags/</b> - miniatures of flags for all countries

<b>/photos/</b> - contains photos of famous people grouped by nationality

<b>/missing</b> - list of countries, for which I was not able to provide photos.

This project is now fairly completed. 


