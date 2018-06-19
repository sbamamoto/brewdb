package server

class Comment {
    String user
    String title
    String commentText
    Comment comment
    static constraints = {
        comment nullable:true
    }
}
