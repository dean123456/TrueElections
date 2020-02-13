package net.thumbtack.school.elections.server;

import net.thumbtack.school.elections.dao.*;
import net.thumbtack.school.elections.daoimpl.*;
import net.thumbtack.school.elections.database.DataBase;
import net.thumbtack.school.elections.general.*;
import net.thumbtack.school.elections.service.*;
import net.thumbtack.school.elections.utils.ElectionsUtils;

import java.io.*;
import java.util.List;

public class Server {

    private static boolean startingIsDone = false;
    private CommonDao commonDao = new CommonDaoImpl();
    private ElectionDao electionDao = new ElectionDaoImpl();
    private VoterDao voterDao = new VoterDaoImpl();
    private ProposalDao cityProposalDao = new ProposalDaoImpl();
    private CandidateDao candidateDao = new CandidateDaoImpl();
    private DataBase database;

    public void startServer(String savedDataFileName) throws IOException, ClassNotFoundException {
        if (savedDataFileName != null) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(savedDataFileName))) {
                if (!startingIsDone) {
                    boolean initSqlSessionFactory = ElectionsUtils.initSqlSessionFactory();
                    if (!initSqlSessionFactory) {
                        throw new RuntimeException("Can't start server");
                    }
                    startingIsDone = true;
                }
                database = (DataBase) ois.readObject();
                commonDao.insertAll(database);
            }
        } else {
            if (!startingIsDone) {
                boolean initSqlSessionFactory = ElectionsUtils.initSqlSessionFactory();
                if (!initSqlSessionFactory) {
                    throw new RuntimeException("Can't start server");
                }
                startingIsDone = true;
                electionDao.newElection();
            }
        }
    }

    public void stopServer(String saveDataFileName) throws IOException {
        if (saveDataFileName != null) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(saveDataFileName))) {
                List<Voter> voterCopy = voterDao.voterCopy();
                for (Voter voter : voterCopy) {
                    System.out.println("voter " + voter.getId());
                    for (Proposal proposal : voter.getProposalList()) {
                        System.out.println("-proposal " + proposal.getId() + " voter " + voter.getId());
                        for (Rating rating : proposal.getRatingList()) {
                            System.out.println("--rating " + rating.getId() + " voter " + rating.getVoter_id() + " proposal " + proposal.getId());
                        }
                    }
                }
                List<Proposal> cityProposalCopy = cityProposalDao.proposalCopy();
                List<Candidate> candidateCopy = candidateDao.candidateCopy();
                Election electionCopy = electionDao.electionCopy();
                List<Vote> voteCopy = electionDao.voteCopy();
                List<AgainstAll> againstAllCopy = electionDao.againstAllCopy();
                database = new DataBase(voterCopy, cityProposalCopy, candidateCopy, electionCopy, voteCopy, againstAllCopy);
                oos.writeObject(database);
                if (startingIsDone) {
                    commonDao.clear();
                    boolean closeSqlSessionFactory = ElectionsUtils.closeSqlSessionFactory();
                    if (!closeSqlSessionFactory) {
                        throw new RuntimeException("Can't stop server.");
                    }
                    startingIsDone = false;
                }
            }
        } else {
            if (startingIsDone) {
                commonDao.clear();
                boolean closeSqlSessionFactory = ElectionsUtils.closeSqlSessionFactory();
                if (!closeSqlSessionFactory) {
                    throw new RuntimeException("Can't stop server.");
                }
                startingIsDone = false;
            }
        }
    }

    public boolean isStartingIsDone() {
        return startingIsDone;
    }

    public void startElection() {
        electionDao.startElection();
    }

    public void stopElection() {
        electionDao.stopElection();
    }

    public String registerVoter(String jsonString) {
        return new VoterService().registerService(jsonString);
    }

    public String logout(String jsonString) {
        return new VoterService().logoutService(jsonString);
    }

    public String login(String jsonString) {
        return new VoterService().loginService(jsonString);
    }

    public String getVoterList(String jsonString) {
        return new VoterService().voterListService(jsonString);
    }

    public String addCandidate(String jsonString) {
        return new CandidateService().addCandidateService(jsonString);
    }

    public String removeCandidate(String jsonString) {
        return new CandidateService().removeCandidateService(jsonString);
    }

    public String agree(String jsonString) {
        return new CandidateService().agreeService(jsonString);
    }

    public String addProposal(String jsonString) {
        return new ProposalService().addProposalService(jsonString);
    }

    public String addProposalRating(String jsonString) {
        return new ProposalService().addProposalRatingService(jsonString);
    }

    public String getProposalListWithRating(String jsonString) {
        return new ProposalService().getProposalListWithRatingService(jsonString);
    }

    public String getProposalListByAllVoters(String jsonString) {
        return new ProposalService().getProposalListByAllVotersService(jsonString);
    }

    public String getProposalListByVoter(String jsonString) {
        return new ProposalService().getProposalListByVoterService(jsonString);
    }

    public String removeProposalRating(String jsonString) {
        return new ProposalService().removeProposalRatingService(jsonString);
    }

    public String addProposalToProgramme(String jsonString) {
        return new ProgrammeService().addProposalToProgrammeService(jsonString);
    }

    public String removeProposalFromProgramme(String jsonString) {
        return new ProgrammeService().removeProposalFromProgrammeService(jsonString);
    }

    public String getOwnProgramme(String jsonString) {
        return new ProgrammeService().getOwnProgrammeService(jsonString);
    }

    public String getCandidateList(String jsonString) {
        return new CandidateService().getCandidateListService(jsonString);
    }

    public String giveVote(String jsonString) {
        return new ElectionService().giveVoteService(jsonString);
    }

    public String getMayor(String jsonString) {
        return new ElectionService().getMayorService(jsonString);
    }

    public DataBase getDatabase() {
        return database;
    }
}
