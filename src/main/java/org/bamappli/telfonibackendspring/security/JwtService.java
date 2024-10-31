package org.bamappli.telfonibackendspring.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.bamappli.telfonibackendspring.Entity.Utilisateur;
import org.bamappli.telfonibackendspring.Repository.UtilisateurRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@AllArgsConstructor
@Service
public class JwtService {
    private final UsersDetailsServiceImpl utilisateurService;
    private final UtilisateurRepo utilisateurRepo;
    
    public Map<String, String> generate(String username) {
        UserDetails utilisateur = this.utilisateurService.loadUserByUsername(username);
        Utilisateur user = utilisateurRepo.findByEmail(username);
        if (!user.isActive()) {
            throw new RuntimeException("Compte non activé. Veuillez vérifier votre email.");
        }
        return Map.of("bearer", this.generateJwt(utilisateur), "role", user.getRole().getNom());
    }

    public String extractUsername(String token) {
        return this.getClaim(token, Claims::getSubject);
    }

    public boolean isTokenExpired(String token) {
        Date expirationDate = getExpirationDateFromToken(token);
        return expirationDate.before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {

        return this.getClaim(token, Claims::getExpiration);
    }

    private <T> T getClaim(String token, Function<Claims, T> function) {
        Claims claims = getAllClaims(token);
        return function.apply(claims);
    }

    private Claims getAllClaims(String token) {

        try {
            return Jwts.parser()
                    .setSigningKey(this.getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Token expired"); // ou lancer une exception personnalisée
        } catch (Exception e) {
            throw new RuntimeException("Token invalide"); // Gestion d'autres exceptions
        }
    }

    private String generateJwt(UserDetails utilisateur) {
        final long currentTime = System.currentTimeMillis();
        final long expirationTime = currentTime + 160 * 60 * 1000;

        final Map<String, Object> claims = Map.of(
                "nom", utilisateur.getUsername(),
                Claims.EXPIRATION, new Date(expirationTime),
                Claims.SUBJECT, utilisateur.getUsername()
        );

        final String bearer = Jwts.builder()
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(expirationTime))
                .setSubject(utilisateur.getUsername())
                .setClaims(claims)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
        return bearer;
    }

    private Key getKey() {
        String ENCRIPTION_KEY = "608f36e92dc66d97d5933f0e6371493cb4fc05b1aa8f8de64014732472303a7c";
        final byte[] decoder = Decoders.BASE64.decode(ENCRIPTION_KEY);
        return Keys.hmacShaKeyFor(decoder);
    }

}
