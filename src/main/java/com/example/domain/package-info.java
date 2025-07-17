@SequenceGenerator(
        name = "todo_gen",
        sequenceName = "todo_seq",
        allocationSize = 25
)
package com.example.domain;

import jakarta.persistence.SequenceGenerator;