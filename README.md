# Files
![Release](https://github.com/peavers/files/workflows/Release/badge.svg)
![Super Linter](https://github.com/peavers/files/workflows/Super%20Linter/badge.svg)
![CodeQL](https://github.com/peavers/files/workflows/CodeQL/badge.svg)

A very simple set of autotools for managing files and stuff 

## What
Similar to autotools plugin for rutorrent but actually works. 

## Running
Docker is always going to be the easiest and cleanest way to get up and running  

Once the containers are running, access on `http://localhost:8080`

Note: All mappings are to the docker container, so remember to mount the volumes you need.

```yaml
version: '3.7'
services:

  files:
    container_name: files
    image: peavers/files:latest
    restart: unless-stopped
    volumes:
      - ${VOLUME_ONE}:/volume_one
      - ${VOLUME_TWO}:/volume_two
      - ${VOLUME_THREE}:/volume_three
    logging:
      options:
        max-size: "2m"
        max-file: "5"

```
## Screenshots
<img src="https://github.com/peavers/files/blob/master/rule-frontend/.screenshots/2.png?raw=true" width="100%">
<img src="https://github.com/peavers/files/blob/master/rule-frontend/.screenshots/3.png?raw=true" width="100%">
<img src="https://github.com/peavers/files/blob/master/rule-frontend/.screenshots/4.png?raw=true" width="100%">
