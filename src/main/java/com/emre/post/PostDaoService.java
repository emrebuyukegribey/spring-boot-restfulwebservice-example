package com.emre.post;

import com.emre.user.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PostDaoService {

    private static List<Post> posts = new ArrayList<>();

    private static int postCount = 11;

    static {

        Post post1 = new Post(1, 1, new Date(), "Adam' nin 1. postu");
        Post post2 = new Post(2, 1, new Date(), "Adam' nin 2. postu");
        Post post3 = new Post(3, 1, new Date(), "Adam' nin 3. postu");
        Post post4 = new Post(4, 1, new Date(), "Adam' nin 4. postu");

        Post post5 = new Post(5, 2, new Date(), "Eve' nin 1. postu");
        Post post6 = new Post(6, 2, new Date(), "Eve' nin 2. postu");
        Post post7 = new Post(7, 2, new Date(), "Eve' nin 3. postu");

        Post post8 = new Post(8, 3, new Date(), "Jack' nin 1. postu");
        Post post9 = new Post(9, 3, new Date(), "Jack' nin 2. postu");
        Post post10 = new Post(10,3, new Date(), "Jack' nin 3. postu");
        Post post11 = new Post(11, 3, new Date(), "Jack' nin 4. postu");

        posts.add(post1);
        posts.add(post2);
        posts.add(post3);
        posts.add(post4);
        posts.add(post5);
        posts.add(post6);
        posts.add(post7);
        posts.add(post8);
        posts.add(post9);
        posts.add(post10);
        posts.add(post11);
    }

    public List<Post> findAll(int id) {
        return posts.stream().filter(post -> post.getUserId() == id).collect(Collectors.toList());
    }

    public Post savePost(int id, Post post)  {
        if(post.getId() == null) {
            post.setId(++postCount);
            post.setUserId(id);
        }
        posts.add(post);
        return post;
    }

    public Post findOne(int id) {
        for (Post post : posts) {
            if(post.getId() == id) {
                return post;
            }
        }
        return null;
    }
}
