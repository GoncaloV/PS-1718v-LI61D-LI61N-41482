package org.gamelog.service;

import org.gamelog.model.Player;
import org.gamelog.repos.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

@Component
public class PlayerService implements UserDetailsManager {
    @Autowired
    private PlayerRepository playerRepository;

    //private InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
    private AuthenticationManager authenticationManager;

    public void save(Player p) {
        playerRepository.save(p);
        /*if(inMemoryUserDetailsManager.userExists(p.getName())) return;
        inMemoryUserDetailsManager.createUser(User.withUsername(p.getName()).password(p.getPassword()).roles("USER").build()); */
    }

    public Iterable<Player> findAll() {
        return playerRepository.findAll();
    }

    public Player findById(long id){
        return playerRepository.findOne(id);
    }

    public Player findByName(String name){
        return playerRepository.findPlayerByName(name);
    }

    public void deleteAll() {
        playerRepository.deleteAll();
    }

    public void delete(Player p){
        playerRepository.delete(p);
    }

  /*  public InMemoryUserDetailsManager getInMemoryUserDetailsManager() {
        return inMemoryUserDetailsManager;
    }*/

    /*public void loadAll() {
        for (Player p : findAll()) {
            inMemoryUserDetailsManager.createUser(User.withUsername(p.getName()).password(p.getPassword()).roles("USER").build());
        }
    }*/


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Player player = findByName(username);
        if (player != null) return User.withUsername(username).password(player.getPassword()).roles("USER").build();
        else throw new UsernameNotFoundException(username);
    }

    @Override
    public void createUser(UserDetails user) {

    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return findByName(username) != null;
    }


    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
}
