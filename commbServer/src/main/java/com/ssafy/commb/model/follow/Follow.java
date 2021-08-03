package com.ssafy.commb.model.follow;

import com.ssafy.commb.model.User;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="Follow")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Follow implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower")
    private User follower;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "following")
    private User following;

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(o==null || getClass() != o.getClass()){
            return false;
        }

        Follow follow = (Follow) o;

        return Objects.equals(getFollower(), follow.getFollower())
                && Objects.equals(getFollowing(), follow.getFollowing());
    }

    @Override
    public int hashCode(){
        return Objects.hash(getFollower(), getFollowing());
    }
}
