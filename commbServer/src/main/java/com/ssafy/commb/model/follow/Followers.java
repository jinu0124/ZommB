package com.ssafy.commb.model.follow;

import lombok.Getter;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@Embeddable
@Getter
public class Followers {

    @OneToMany(
            mappedBy = "following",
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST,
            orphanRemoval = false
    )
    private List<Follow> followers;

    public void add(Follow follow){
        if(this.followers.contains((follow))){
            return;
        }
        followers.add(follow);
    }

    public void remove(Follow follow){
        if(!this.followers.contains((follow))){
            return;
        }
        followers.remove(follow);
    }

    public int count(){
        return followers.size();
    }
}
