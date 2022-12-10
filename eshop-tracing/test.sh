BASEDIR=$(dirname "$0")

kubectl apply -f $BASEDIR/k8s

echo "Wait 20 seconds until k8s deployments are ready ..."

n=1
while [ $n -le 20 ]
do
       sleep 1
       echo "$n seconds"
       (( n++ ))
done

echo "Forward port to localhost, if it fails,  please wait until all pods are ready and run the following commands manually ..."
kill `ps -ef|grep "kubectl port-forward"|awk '{print $2}'`
kubectl port-forward service/eshop 8080:8080 &
kubectl port-forward service/jaeger 16686:16686 &

echo "Wait 10 seconds until port forwarding is ready ..."
sleep 10

echo "Call the eShop microservice, if it fails,  please wait a moment and run the following commands manually ..."

curl 127.0.0.1:8080/checkout --header "user: huabing"
