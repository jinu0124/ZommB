package com.ssafy.commb.model;

import com.ssafy.commb.model.follow.Follow;
import com.ssafy.commb.model.follow.Followers;
import com.ssafy.commb.model.follow.Followings;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="User")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String email;

    private String name;

    private String nickname;

    private String password;

    private int year;

    private char gender;

    private String role;

    private int bookmark;

    private int pencil;

    private int bookmarkOn;

    private int pencilOn;

    private String fileUrl;

    /* 유저의 피드 */
    @OneToMany(mappedBy = "user")
//    private List<Feed> feeds = new ArrayList<Feed>();
    private List<Thumb> feeds = new ArrayList<Thumb>();

    /* 유저의 서재/북카트 */
    @OneToMany(mappedBy = "user")
    private List<BookShelves> bookShelves = new ArrayList<BookShelves>();

    /* 유저의 주간 이벤트 참여 */
    @OneToMany(mappedBy = "user")
    private List<WeeklyEventParticipate> weeklyEventParticipates = new ArrayList<WeeklyEventParticipate>();

    /* 유저의 상단도서 */
    @ManyToMany
    @JoinTable(name="Top_Book", joinColumns = @JoinColumn(name="user_id")
            , inverseJoinColumns = @JoinColumn(name="comment_id"))
    private List<Book> topBooks = new ArrayList<Book>();

    @OneToMany(mappedBy = "user")
    private List<UserKeyword> userKeywords = new ArrayList<UserKeyword>();

    @OneToMany(mappedBy = "user")
    private List<DailyEventParticipate> dailyEventParticipates = new ArrayList<DailyEventParticipate>();

    @OneToMany(mappedBy = "user")
    private List<CommentThumb> comments = new ArrayList<>();

    @OneToOne(mappedBy = "user")
    private ConfirmationToken confirmationToken;

    @Embedded
    private Followers followers;

    @Embedded
    private Followings followings;

    public void follow(User following){

        Follow follow = new Follow();
        follow.setFollower(this);
        follow.setFollowing(following);
        this.followings.add(follow);
        following.followers.add(follow);
    }

    public void unfollow(User following){
        Follow follow = new Follow();
        follow.setFollower(this);
        follow.setFollowing(following);
        this.followings.remove(follow);
        following.followers.remove(follow);
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if( o == null || getClass() != o.getClass()){
            return false;
        }

        User user = (User) o;
        return Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode(){
        return Objects.hash(getId());
    }

    @Override
    public String toString(){

        return Integer.toString(getId());
    }

    public User(String email, String password, String name, String nickname){
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
    }
}
