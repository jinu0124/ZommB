package com.ssafy.commb.model.follow;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@Embeddable
public class Followings {

    @OneToMany(
            mappedBy = "follower",
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST,
            orphanRemoval = true
    )
    private List<Follow> followings;

    public void add(Follow follow){
        if(this.followings.contains((follow))){
            return;
        }
        followings.add(follow);
    }

    public void remove(Follow follow){
        if(!this.followings.contains((follow))){
            return;
        }
        followings.remove(follow);
    }

    public int count(){
        return followings.size();
    }
}
