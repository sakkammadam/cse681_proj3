import React, { useState } from "react";
import axios from "axios";
import { Container, Typography, Card, CardContent, TextField, Button, Table, TableHead, TableRow, TableCell, TableBody } from "@mui/material";

const App = () => {
    const [teamName, setTeamName] = useState("");
    const [stats, setStats] = useState(null);
    const [error, setError] = useState(null);

    const fetchStats = async () => {
        setError(null);
        setStats(null);
        try {
            const response = await axios.get(`http://localhost:8080/api/stats?teamName=${encodeURIComponent(teamName)}`);
            setStats(response.data || []);
        } catch (err) {
            setError("Invalid team name or no data available.");
        }
    };

    return (
        <Container>
            <Typography variant="h4" gutterBottom>
                Sports Stats Dashboard
            </Typography>
            <TextField
                label="Enter Team Name"
                value={teamName}
                onChange={(e) => setTeamName(e.target.value)}
                fullWidth
            />
            <Button variant="contained" color="primary" onClick={fetchStats} style={{ marginTop: 10 }}>
                Get Stats
            </Button>

            {error && <Typography color="error" style={{ marginTop: 10 }}>{error}</Typography>}

            {stats && stats.length > 0 && (
                <Card style={{ marginTop: 20 }}>
                    <CardContent>
                        <Typography variant="h6">Match Results for {teamName}</Typography>
                        <div style={{ overflowX: "auto" }}>
                            <Table>
                                <TableHead>
                                    {stats.length > 0 &&
                                        Object.keys(stats[0])
                                            //  let's exclude statIdCode, gameCode and teamCode keys
                                            .filter((key) => key  !== "statIdCode" && key  !== "gameCode" && key !== "teamCode")
                                            .map((key) => (
                                                    <TableCell sx={{ fontSize: "0.9rem", fontWeight: "bold", padding: "6px" }}>
                                                        {key.replace(/([A-Z])/g, " $1").replace(/^./, (str) => str.toUpperCase())}
                                                    </TableCell>
                                                )
                                            )
                                    }
                                </TableHead>
                                <TableBody>
                                    {stats.map((game,index) => (
                                            <TableRow key={index}>
                                                {
                                                    Object.keys(game)
                                                        //  let's exclude values associated with statIdCode, gameCode and teamCode keys
                                                        .filter((key) => key  !== "statIdCode" && key  !== "gameCode" && key !== "teamCode")
                                                        // Print out the value within the TableCell
                                                        .map((key) => (<TableCell key={key}>{game[key]}</TableCell>))
                                                }
                                            </TableRow>
                                        )
                                    )
                                    }
                                </TableBody>
                            </Table>
                        </div>
                    </CardContent>
                </Card>
            )}
        </Container>
    );
};

export default App;

