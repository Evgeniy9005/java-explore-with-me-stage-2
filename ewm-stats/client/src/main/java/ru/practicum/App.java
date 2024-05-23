package ru.practicum;


import dto.EndpointHitDto;

public class App
{
    public static void main( String[] args )
    {
        EndpointHitDto endpointHit = EndpointHitDto.builder().build();
        System.out.println(endpointHit);
      //  System.out.println( "Hello World!");
    }
}
