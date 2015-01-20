#!/bin/bash

## v 0.0.1

# ex. $1 = http://en.wikipedia.org/wiki/Ronaldo
function download_picture {

   #echo "download_picture ($1, $2)"
   GET_INFOBOX_SECTION=$(curl -s -HGET $1 | sed -n -e '/infobox/,/<\/td>/ p')
   HTTP_PREFIX='http:'
   GET_IMAGE_URL=$HTTP_PREFIX$(echo "$GET_INFOBOX_SECTION" | grep -oh 'src="[^"]*"' | grep -oh '//[^"]*')

   if [ "$GET_IMAGE_URL" == "$HTTP_PREFIX" ]; then
      return 0
   fi

   FILE_NAME=$(basename $GET_IMAGE_URL)
   EXTENSION="${FILE_NAME##*.}"
   wget $GET_IMAGE_URL -O $2.$EXTENSION
}


WIKI_PREFIX='http://en.wikipedia.org'
GET_NATIONS=$(curl -s -HGET http://en.wikipedia.org/wiki/Lists_of_people_by_nationality | grep -oh '<a href="/wiki/.*List.*</a>' | sed -n -e '/Afghans/,/Zimbabweans/ p')

rm -rf test
mkdir test
cd test

IFS=$'\n'

for nation in $GET_NATIONS
do
   NATION_NAME=$(echo "$nation" | grep -oh '>.*<' | grep -oh '[a-zA-Z ]*')
   NATION_URL=$WIKI_PREFIX$(echo "$nation" | grep -oh '/wiki/[^"]*')
   mkdir $NATION_NAME
   cd $NATION_NAME
   echo "$NATION_NAME: $NATION_URL"

   GET_PEOPLE=$(curl -s -HGET $NATION_URL | grep -Eoh '<(td|li)><a href="/wiki/.*</a></(td|li)>')
   for person in $GET_PEOPLE
   do
      echo -e "\n\n\n\n\n"
      PERSON_NAME=$(echo "$person" | grep -Eoh '>[^<]+<' | grep -Eoh '[^<>]+')
      PERSON_URL=$WIKI_PREFIX$(echo "$person" | grep -oh '/wiki/[^"]*')
      echo "$PERSON_NAME: $PERSON_URL"
      download_picture $PERSON_URL $PERSON_NAME
   done

   cd ..

done

unset IFS
