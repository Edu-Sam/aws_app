/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.studentexample;

import com.amazonaws.regions.Region;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;


public class SavePersonHandler implements RequestHandler<PersonRequest,PersonResponse>{
    
    private DynamoDB dynamoDB;
    private String  DYNAMO_DB_TABLE_NAME="student";
    private Regions REGION=Regions.US_WEST_2;
    
    @Override
    public PersonResponse handleRequest(PersonRequest personRequest,Context context){
        this.initDynamoDBClient();
        
        persistData(personRequest);
        
        PersonResponse personResponse=new PersonResponse();
        personResponse.setMessage("Saved Successfully!!!!");
        return personResponse;
    }
    
    private PutItemOutcome persistData(PersonRequest personRequest) throws ConditionalCheckFailedException{
        return this.dynamoDB.getTable(DYNAMO_DB_TABLE_NAME)
                .putItem(
                new PutItemSpec().withItem(new Item().withString("firstName", personRequest.getFirstName())
                .withString("lastName",personRequest.getLastName()))
                );
        
    }
    
    private void initDynamoDBClient(){
        AmazonDynamoDBClient client=new AmazonDynamoDBClient();
        client.setRegion(Region.getRegion(REGION));
        this.dynamoDB=new DynamoDB(client);
       
        
    }
    
}