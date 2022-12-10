# Build parameters
OUT?=./target
DOCKER_TMP?=$(OUT)/docker_temp/
DOCKER_TAG?=zhaohuabing/liveproject-tracing:v3.3
BINARY_NAME?=$(OUT)/tracing-demo-0.0.1-SNAPSHOT.jar

docker-build: build
	rm -rf $(DOCKER_TMP)
	mkdir $(DOCKER_TMP)
	cp $(BINARY_NAME) $(DOCKER_TMP)
	cp ./Dockerfile $(DOCKER_TMP)Dockerfile
	docker build -t $(DOCKER_TAG) $(DOCKER_TMP)
	rm -rf $(DOCKER_TMP)
build:
	mvn package
docker-push: docker-build
	docker push $(DOCKER_TAG)
clean:
	rm -rf $(OUT)
test: docker-build
	./test.sh

.PHONY: build docker-build docker-push clean
