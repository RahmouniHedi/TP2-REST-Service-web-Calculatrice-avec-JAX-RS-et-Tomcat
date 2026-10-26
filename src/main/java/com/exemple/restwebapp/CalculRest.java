package com.exemple.restwebapp;

import jakarta.ws.rs.*;


import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/calcul")
public class CalculRest {
    private final Calculatrice calculService = new Calculatrice();
    @GET
    @Path("/somme")
    @Produces(MediaType.TEXT_PLAIN)
    public Response somme(@QueryParam("a") int a, @QueryParam("b") int b) {
        int result = calculService.somme(a, b);
        return Response.ok(result).build();
    }
    @GET
    @Path("/soustraction")
    @Produces(MediaType.TEXT_PLAIN)
    public Response soustraction(@QueryParam("a") int a, @QueryParam("b") int b) {
        int result = calculService.soustraction(a, b);
        return Response.ok(result).build();
    }
    @GET
    @Path("/produit/{a}/{b}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response produitChemin(@PathParam("a") int a, @PathParam("b") int b) {
        int result = calculService.produit(a, b);
        return Response.ok(result).build();
    }
    @GET
    @Path("/division")
    @Produces(MediaType.TEXT_PLAIN)
    public Response division(@QueryParam("a") int a, @QueryParam("b") int b) {
        try {
            double result = calculService.division(a, b);
            return Response.ok(result).build();
        } catch (ArithmeticException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
    // E.2 : Somme détaillée en JSON
    @GET
    @Path("/somme/detail")
    @Produces(MediaType.APPLICATION_JSON)
    public Response sommeDetail(@QueryParam("a") int a, @QueryParam("b") int b) {
        Resultat resultat = new Resultat("somme", a, b, calculService.somme(a, b));
        return Response.ok(resultat).build();
    }
    // E.4 : Division détaillée en JSON (retourne 400 si b == 0)
    @GET
    @Path("/division/detail")
    @Produces(MediaType.APPLICATION_JSON)
    public Response divisionDetail(@QueryParam("a") int a, @QueryParam("b") int b) {
        if (b == 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"erreur\":\"Erreur : division par 0 impossible\"}")
                    .build();
        }
        Resultat resultat = new Resultat("division", a, b, calculService.division(a, b));
        return Response.ok(resultat).build();
    }
}